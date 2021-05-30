package net.astrocube.commons.core.service;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.inject.Inject;
import net.astrocube.api.core.model.Model;
import net.astrocube.api.core.model.ModelMeta;
import net.astrocube.api.core.model.PartialModel;
import net.astrocube.api.core.redis.Redis;
import net.astrocube.api.core.service.create.CreateRequest;
import net.astrocube.api.core.service.delete.DeleteRequest;
import net.astrocube.api.core.service.find.FindRequest;
import net.astrocube.api.core.service.paginate.PaginateResult;
import net.astrocube.api.core.service.query.QueryRequest;
import net.astrocube.api.core.service.query.QueryResult;
import net.astrocube.api.core.service.update.UpdateRequest;
import redis.clients.jedis.Jedis;

import java.io.IOException;
import java.util.*;

public class DirectRedisModelService<Complete extends Model, Partial extends PartialModel>
	extends CoreModelService<Complete, Partial> {

	private @Inject Redis redis;
	private @Inject ObjectMapper objectMapper;

	protected ModelMeta<Complete, Partial> modelMeta;
	protected JavaType queryResultTypeToken;
	protected JavaType paginateResultTypeToken;

	public @Inject
	DirectRedisModelService(
		ObjectMapper mapper,
		ModelMeta<Complete, Partial> modelMeta
	) {
		super(mapper, modelMeta);
		this.modelMeta = modelMeta;
		this.queryResultTypeToken = mapper.getTypeFactory().constructParametricType(QueryResult.class, modelMeta.getCompleteType());
		this.paginateResultTypeToken = mapper.getTypeFactory().constructParametricType(PaginateResult.class, modelMeta.getCompleteType());
	}

	@Override
	public Complete createSync(CreateRequest<Partial> request) throws Exception {

		ObjectNode node = mapper.valueToTree(request.getModel());

		node.put("_id", UUID.randomUUID().toString());
		String json = node.toString();

		Complete model = getModel(json);

		try (Jedis client = redis.getRawConnection().getResource()) {

			client.set(
				modelMeta.getRouteKey() + ':' + UUID.randomUUID(),
				mapper.writeValueAsString(model)
			);
		}

		return model;
	}

	@Override
	public Complete findSync(FindRequest<Complete> findModelRequest) throws Exception {
		try (Jedis client = redis.getRawConnection().getResource()) {
			String json = client.get(modelMeta.getRouteKey() + ':' + findModelRequest.getId());
			if (json == null) {
				return null;
			} else {
				return mapper.readValue(json, modelMeta.getCompleteType());
			}
		}
	}

	@Override
	public void deleteSync(DeleteRequest<Complete> deleteRequest) throws Exception {
		try (Jedis client = redis.getRawConnection().getResource()) {
			client.del(modelMeta.getRouteKey() + ':' + deleteRequest.getId());
		}
	}

	@Override
	public QueryResult<Complete> querySync(QueryRequest<Complete> queryRequest) throws Exception {
		Set<Complete> found = new HashSet<>();
		System.out.println("querySync");
		try (Jedis client = redis.getRawConnection().getResource()) {
			for (String key : client.keys(modelMeta.getRouteKey() + ":*")) {
				System.out.println("Key " + key);
				String json = client.get(key);
				JsonNode node = mapper.readTree(json);
				if (contains(node, queryRequest.getBsonQuery())) {
					System.out.println("The node read from the key '" + key + "' contains the given QueryRequest's bson query, node " + node);
					Complete value = mapper.readValue(
						node.toString(),
						modelMeta.getCompleteType()
					);
					found.add(value);
				}
			}
		}
		return () -> found;
	}

	@Override
	public Complete updateSync(UpdateRequest<Partial> request) throws Exception {
		try (Jedis client = redis.getRawConnection().getResource()) {
			Partial partial = request.getModel();
			String id = ((Model) partial).getId();

			String json = mapper.writeValueAsString(partial);

			client.set(
				modelMeta.getRouteKey() + ':' + id,
				json
			);

			System.out.println("Updating " + json);

			/*@SuppressWarnings("unchecked")
			Complete value = (Complete) partial;
			return value;*/

			return getModel(json);
		}
	}

	private boolean contains(JsonNode root, JsonNode node) {
		if (root == null) {
			return false;
		} else if (root.isValueNode()) {
			System.out.println("2");
			return node.isValueNode() && root.equals(node);
		} else if (root.isArray()) {
			if (node.isArray()) {
				System.out.println("node is Array");
				ArrayNode containerArray = (ArrayNode) root;
				ArrayNode nodeArray = (ArrayNode) node;
				Set<JsonNode> containedElements = new HashSet<>();
				for (JsonNode content : containerArray) {
					System.out.println("Content " + content.toString());
					containedElements.add(content);
				}
				for (JsonNode element : nodeArray) {
					if (!containedElements.contains(element)) {
						System.out.println("No contained elements");
						return false;
					}
				}
				System.out.println("true");
				return true;
			} else {
				return false;
			}
		}
		Iterator<Map.Entry<String, JsonNode>> fields = node.fields();
		while (fields.hasNext()) {
			Map.Entry<String, JsonNode> field = fields.next();
			String key = field.getKey();
			JsonNode value = field.getValue();
			JsonNode rootValue = root.get(key);

			if (!contains(rootValue, value)) {
				return false;
			}
		}
		return true;
	}

	private <T extends Model> T getModel(String json) throws IOException {
		return objectMapper.readValue(json, mapper.constructType(getCompleteType()));
	}

}
