package net.astrocube.commons.core.service;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import net.astrocube.api.core.model.Model;
import net.astrocube.api.core.model.ModelMeta;
import net.astrocube.api.core.model.PartialModel;
import net.astrocube.api.core.redis.Redis;
import net.astrocube.api.core.service.create.CreateRequest;
import net.astrocube.api.core.service.delete.DeleteRequest;
import net.astrocube.api.core.service.find.FindRequest;
import net.astrocube.api.core.service.paginate.PaginateRequest;
import net.astrocube.api.core.service.paginate.PaginateResult;
import net.astrocube.api.core.service.query.QueryRequest;
import net.astrocube.api.core.service.query.QueryResult;
import net.astrocube.api.core.service.update.UpdateRequest;
import redis.clients.jedis.Jedis;

import java.util.*;

public class DirectRedisModelService<Complete extends Model, Partial extends PartialModel>
        extends AsyncModelService<Complete, Partial> {

    private @Inject Redis redis;
    protected final ObjectMapper mapper;

    protected ModelMeta<Complete, Partial> modelMeta;
    protected JavaType queryResultTypeToken;
    protected JavaType paginateResultTypeToken;

    public @Inject DirectRedisModelService(
            ObjectMapper mapper,
            ModelMeta<Complete, Partial> modelMeta
    ) {
        this.modelMeta = modelMeta;
        this.mapper = mapper;
        this.queryResultTypeToken = mapper.getTypeFactory().constructParametricType(QueryResult.class, modelMeta.getCompleteType());
        this.paginateResultTypeToken = mapper.getTypeFactory().constructParametricType(PaginateResult.class, modelMeta.getCompleteType());
    }

    @Override
    public JavaType getCompleteType() {
        return modelMeta.getCompleteType();
    }

    @Override
    public JavaType getPartialType() {
        return modelMeta.getPartialType();
    }

    @Override
    public QueryResult<Complete> querySync(QueryRequest<Complete> request) throws Exception {
        Set<Complete> found = new HashSet<>();
        try (Jedis client = openResource()) {
            for (String key : client.keys(modelMeta.getRouteKey() + ":*")) {
                String json = client.get(key);
                JsonNode node = mapper.readTree(json);
                if (contains(node, request.getBsonQuery())) {
                    Complete value = mapper.readValue(
                            node.asText(),
                            modelMeta.getCompleteType()
                    );
                    found.add(value);
                }
            }
        }
        return () -> found;
    }

    private boolean contains(JsonNode root, JsonNode node) {
        if (root == null) {
            return false;
        } else if (root.isValueNode()) {
            return node.isValueNode() && root.equals(node);
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

    @Override
    public PaginateResult<Complete> paginateSync(PaginateRequest<Complete> paginateRequest) throws Exception {
        throw new UnsupportedOperationException("Redis model service doesn't support pagination");
    }

    @Override
    public Complete findSync(FindRequest<Complete> findModelRequest) throws Exception {
        try (Jedis client = openResource()) {
            String json = client.get(modelMeta.getRouteKey() + ':' + findModelRequest.getId());
            if (json == null) {
                return null;
            } else {
                return mapper.readValue(json, modelMeta.getCompleteType());
            }
        }
    }

    @Override
    public void deleteSync(DeleteRequest<Complete> deleteRequest) {
        try (Jedis client = openResource()) {
            client.del(modelMeta.getRouteKey() + ':' + deleteRequest.getId());
        }
    }

    @Override
    public Complete updateSync(UpdateRequest<Partial> request) throws Exception {
        try (Jedis client = openResource()) {
            Partial partial = request.getModel();
            String id = ((Model) partial).getId();

            // TODO: Tomato me dijo que lo dejara asi, yo creo k no deberia ser asi pero weno -Yushu
            client.set(
                    id,
                    mapper.writeValueAsString(partial)
            );
            @SuppressWarnings("unchecked")
            Complete value = (Complete) partial;
            return value;
        }
    }

    @Override
    public Complete createSync(CreateRequest<Partial> request) throws Exception {
        return null;
    }

    private Jedis openResource() {
        return redis.getRawConnection().getResource();
    }

}
