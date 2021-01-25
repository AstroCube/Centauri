package net.astrocube.commons.core.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import net.astrocube.api.core.concurrent.ExecutorServiceProvider;
import net.astrocube.api.core.http.HttpClient;
import net.astrocube.api.core.http.RequestOptions;
import net.astrocube.api.core.model.Model;
import net.astrocube.api.core.model.ModelMeta;
import net.astrocube.api.core.model.PartialModel;
import net.astrocube.api.core.redis.Redis;
import net.astrocube.api.core.service.create.CreateRequest;
import net.astrocube.api.core.service.find.FindRequest;
import net.astrocube.api.core.service.update.UpdateRequest;
import net.astrocube.commons.core.http.CoreRequestOptions;

import java.util.HashMap;

@SuppressWarnings("All")
public class RedisModelService<Complete extends Model, Partial extends PartialModel> extends CoreModelService<Complete, Partial> {

    @Inject private Redis redis;
    @Inject private HttpClient httpClient;
    private RedisRequestCallable<Complete> redisRequestCallabe;
    @Inject private ObjectMapper objectMapper;

    @Inject
    RedisModelService(
            ObjectMapper mapper,
            ModelMeta<Complete, Partial> modelMeta,
            ExecutorServiceProvider executorServiceProvider
    ) {
        super(mapper, modelMeta, executorServiceProvider);
        this.redisRequestCallabe =
                new RedisRequestCallable<>(objectMapper, redis, modelMeta);
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

}