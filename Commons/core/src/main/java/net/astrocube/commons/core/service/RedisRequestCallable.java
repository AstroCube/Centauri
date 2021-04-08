package net.astrocube.commons.core.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpResponse;
import lombok.AllArgsConstructor;
import net.astrocube.api.core.http.RequestCallable;
import net.astrocube.api.core.model.Model;
import net.astrocube.api.core.model.ModelMeta;
import net.astrocube.api.core.redis.Redis;
import net.astrocube.commons.core.http.resolver.RequestExceptionResolverUtil;
import redis.clients.jedis.Jedis;

@AllArgsConstructor
@SuppressWarnings("all")
public class RedisRequestCallable<T extends Model> implements RequestCallable<T> {

    private final ObjectMapper mapper;
    private final Redis redis;
    private final ModelMeta<T, ?> modelMeta;

    @Override
    public T call(HttpRequest request) throws Exception {
        final HttpResponse response = request.execute();
        final String json = response.parseAsString();
        int statusCode = response.getStatusCode();

        if (statusCode < 400) {
            try (Jedis jedis = redis.getRawConnection().getResource()) {
                T model = (T) mapper.readValue(json, mapper.constructType(modelMeta.getCompleteType()));

                String key = modelMeta.getRouteKey() + ":" + model.getId();

                jedis.set(key, json);
                jedis.expire(key, modelMeta.getCache());

                return model;
            } catch (Exception exception) {
                exception.printStackTrace();
                throw new Exception("Parsing of " + modelMeta.getCompleteType() + " failed");
            }
        } else {
            throw RequestExceptionResolverUtil.generateException(json, statusCode);
        }
    }
}