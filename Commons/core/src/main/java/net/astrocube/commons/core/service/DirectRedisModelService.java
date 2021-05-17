package net.astrocube.commons.core.service;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
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
		String id = UUID.randomUUID().toString();
		ObjectNode node = (ObjectNode) mapper.readTree(
			mapper.writeValueAsString(request.getModel())
		);

		node.put("_id", id);
		String json = node.asText();

		try (Jedis client = redis.getRawConnection().getResource()) {

			System.out.println("Creating model json " + json);

			client.set(
				modelMeta.getRouteKey() + ':' + id,
				json
			);
		}

		return getModel(json);
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

	private <T extends Model> T getModel(String json) throws IOException {
		return objectMapper.readValue(json, mapper.constructType(getCompleteType()));
	}

}
