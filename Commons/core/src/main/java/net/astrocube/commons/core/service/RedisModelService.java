package net.astrocube.commons.core.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpResponse;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.astrocube.api.core.concurrent.ExecutorServiceProvider;
import net.astrocube.api.core.http.HttpClient;
import net.astrocube.api.core.http.RequestCallable;
import net.astrocube.api.core.http.RequestOptions;
import net.astrocube.api.core.model.Model;
import net.astrocube.api.core.model.ModelMeta;
import net.astrocube.api.core.model.PartialModel;
import net.astrocube.api.core.redis.Redis;
import net.astrocube.api.core.service.create.CreateRequest;
import net.astrocube.api.core.service.find.FindRequest;
import net.astrocube.api.core.service.update.UpdateRequest;
import net.astrocube.commons.core.http.CoreRequestOptions;
import net.astrocube.commons.core.http.resolver.RequestExceptionResolverUtil;
import redis.clients.jedis.Jedis;

import java.util.HashMap;

@Singleton
@SuppressWarnings("All")
public class RedisModelService<Complete extends Model, Partial extends PartialModel> extends CoreModelService<Complete, Partial> {

    @Inject private Redis redis;
    @Inject private HttpClient httpClient;
    private RedisRequestCallabe<Complete> redisRequestCallabe;
    @Inject private ObjectMapper objectMapper;

    @Inject
    RedisModelService(
            ModelMeta<Complete, Partial> modelMeta,
            ExecutorServiceProvider executorServiceProvider
    ) {
        super(modelMeta, executorServiceProvider);
        this.redisRequestCallabe = new RedisRequestCallabe<>();
    }

    @Override
    public Complete updateSync(UpdateRequest<Partial> request) throws Exception {
        return httpClient.executeRequestSync(
                modelMeta.getRouteKey(),
                redisRequestCallabe,
                new CoreRequestOptions(
                        RequestOptions.Type.PUT,
                        new HashMap<>(),
                        this.objectMapper.writeValueAsString(request.getModel()),
                        null
                )
        );
    }


    @Override
    public Complete findSync(FindRequest<Complete> findModelRequest) throws Exception {
        return httpClient.executeRequestSync(
                this.modelMeta.getRouteKey() + "/" + findModelRequest.getId(),
                this.redisRequestCallabe,
                new CoreRequestOptions(
                        RequestOptions.Type.GET,
                        new HashMap<>(),
                        "",
                        null
                )
        );
    }

    @Override
    public Complete createSync(CreateRequest<Partial> request) throws Exception {
        return httpClient.executeRequestSync(
                this.modelMeta.getRouteKey(),
                this.redisRequestCallabe,
                new CoreRequestOptions(
                        RequestOptions.Type.POST,
                        new HashMap<>(),
                        this.objectMapper.writeValueAsString(request.getModel()),
                        null
                )
        );
    }

    private class RedisRequestCallabe<T extends Model> implements RequestCallable<T> {

        @Override
        public T call(HttpRequest request) throws Exception {
            final HttpResponse response = request.execute();
            final String json = response.parseAsString();
            int statusCode = response.getStatusCode();

            if (statusCode < 400) {
                try (Jedis jedis = redis.getRawConnection().getResource()) {
                    T model = (T) objectMapper.readValue(json, mapper.constructType(getCompleteType().getType()));
                    String key = modelMeta.getRouteKey() + ":" + model.getId();
                    jedis.set(key, json);
                    jedis.expire(key, modelMeta.getCache());
                    return model;
                } catch (Exception exception) {
                    exception.printStackTrace();
                    throw new Exception("Parsing of " + getCompleteType().getType() + " failed");
                }
            } else {
                throw RequestExceptionResolverUtil.generateException(json, statusCode);
            }
        }
    }
}