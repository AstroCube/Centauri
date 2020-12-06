package net.astrocube.commons.core.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.reflect.TypeParameter;
import com.google.common.reflect.TypeToken;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.inject.Inject;
import net.astrocube.api.core.concurrent.*;
import net.astrocube.api.core.http.HttpClient;
import net.astrocube.api.core.http.RequestOptions;
import net.astrocube.api.core.model.Model;
import net.astrocube.api.core.model.ModelMeta;
import net.astrocube.api.core.model.PartialModel;
import net.astrocube.api.core.service.create.CreateRequest;
import net.astrocube.api.core.service.create.CreateService;
import net.astrocube.api.core.service.delete.DeleteRequest;
import net.astrocube.api.core.service.delete.DeleteService;
import net.astrocube.api.core.service.find.FindRequest;
import net.astrocube.api.core.service.find.FindService;
import net.astrocube.api.core.service.paginate.PaginateRequest;
import net.astrocube.api.core.service.paginate.PaginateResult;
import net.astrocube.api.core.service.paginate.PaginateService;
import net.astrocube.api.core.service.query.QueryRequest;
import net.astrocube.api.core.service.query.QueryResult;
import net.astrocube.api.core.service.query.QueryService;
import net.astrocube.api.core.service.update.UpdateRequest;
import net.astrocube.api.core.service.update.UpdateService;
import net.astrocube.commons.core.http.CoreRequestCallable;
import net.astrocube.commons.core.http.CoreRequestOptions;

import java.util.HashMap;

@SuppressWarnings("UnstableApiUsage")
public class CoreModelService<Complete extends Model, Partial extends PartialModel> implements
        CreateService<Complete, Partial>,
        DeleteService<Complete>,
        FindService<Complete>,
        PaginateService<Complete>,
        QueryService<Complete>,
        UpdateService<Complete, Partial>
{

    @Inject protected HttpClient httpClient;
    @Inject protected ObjectMapper mapper;
    private final ListeningExecutorService listeningExecutorService;

    protected ModelMeta<Complete, Partial> modelMeta;
    protected TypeToken<QueryResult<Complete>> queryResultTypeToken;
    protected TypeToken<PaginateResult<Complete>> paginateResultTypeToken;

    public @Inject
    CoreModelService(ModelMeta<Complete, Partial> modelMeta, ExecutorServiceProvider executorServiceProvider) {
        this.modelMeta = modelMeta;
        this.listeningExecutorService = executorServiceProvider.getRegisteredService();
        this.queryResultTypeToken = new TypeToken<QueryResult<Complete>>(){}.where(new TypeParameter<Complete>(){}, this.modelMeta.getCompleteType());
        this.paginateResultTypeToken = new TypeToken<PaginateResult<Complete>>(){}.where(new TypeParameter<Complete>(){}, this.modelMeta.getCompleteType());

        System.out.println(modelMeta.getCompleteType().getType().getTypeName() + ":" + this);
    }

    @Override
    public TypeToken<Complete> getCompleteType() {
        return modelMeta.getCompleteType();
    }

    @Override
    public AsyncResponse<QueryResult<Complete>> query(QueryRequest<Complete> queryRequest) {
        return new SimpleAsyncResponse<>(this.listeningExecutorService.submit(() -> {
            try {
                return new WrappedResponse<>(Response.Status.SUCCESS, querySync(queryRequest), null);
            } catch (Exception exception) {
                return new WrappedResponse<>(Response.Status.ERROR, null, exception);
            }
        }), listeningExecutorService);
    }

    @Override
    public QueryResult<Complete> querySync(QueryRequest<Complete> queryRequest) throws Exception {
        return this.httpClient.executeRequestSync(
                modelMeta.getRouteKey() + "/list",
                new CoreRequestCallable<>(this.queryResultTypeToken, mapper),
                new CoreRequestOptions(
                        RequestOptions.Type.POST,
                        new HashMap<>(),
                        queryRequest.getBsonQuery() == null ? "{}"
                                : this.mapper.writeValueAsString(queryRequest.getBsonQuery()),
                        "?page=-1"
                )
        );
    }

    @Override
    public TypeToken<Partial> getPartialType() {
        return modelMeta.getPartialType();
    }

    @Override
    public Complete updateSync(UpdateRequest<Partial> request) throws Exception {
        return this.httpClient.executeRequestSync(
                modelMeta.getRouteKey(),
                new CoreRequestCallable<>(getCompleteType(), mapper),
                new CoreRequestOptions(
                        RequestOptions.Type.PUT,
                        this.mapper.writeValueAsString(request.getModel())
                )
        );
    }

    @Override
    public AsyncResponse<Complete> update(UpdateRequest<Partial> request) {
        return new SimpleAsyncResponse<>(this.listeningExecutorService.submit(() -> {
            try {
                return new WrappedResponse<>(Response.Status.SUCCESS, updateSync(request), null);
            } catch (Exception exception) {
                return new WrappedResponse<>(Response.Status.ERROR, null, exception);
            }
        }), listeningExecutorService);
    }

    @Override
    public Complete createSync(CreateRequest<Partial> request) throws Exception {
        return this.httpClient.executeRequestSync(
                modelMeta.getRouteKey(), // https://perseus.astrocube.net/api/friend/
                new CoreRequestCallable<>(getCompleteType(), mapper),
                new CoreRequestOptions(
                        RequestOptions.Type.POST,
                        this.mapper.writeValueAsString(request.getModel())
                )
        );
    }

    @Override
    public AsyncResponse<Complete> create(CreateRequest<Partial> request) {
        return new SimpleAsyncResponse<>(this.listeningExecutorService.submit(() -> {
            try {
                return new WrappedResponse<>(Response.Status.SUCCESS, createSync(request), null);
            } catch (Exception exception) {
                return new WrappedResponse<>(Response.Status.ERROR, null, exception);
            }
        }), listeningExecutorService);
    }

    @Override
    public PaginateResult<Complete> paginateSync(PaginateRequest<Complete> paginateRequest) throws Exception {
        return this.httpClient.executeRequestSync(
                modelMeta.getRouteKey() + "/list",
                new CoreRequestCallable<>(this.paginateResultTypeToken, mapper),
                new CoreRequestOptions(
                        RequestOptions.Type.POST,
                        new HashMap<>(),
                        paginateRequest.getBsonQuery() == null ? "{}" :
                                this.mapper.writeValueAsString(paginateRequest.getBsonQuery()),
                        paginateRequest.getPaginateQuery()
                )
        );
    }

    @Override
    public AsyncResponse<PaginateResult<Complete>> paginate(PaginateRequest<Complete> paginateRequest) {
        return new SimpleAsyncResponse<>(this.listeningExecutorService.submit(() -> {
            try {
                return new WrappedResponse<>(Response.Status.SUCCESS,paginateSync(paginateRequest), null);
            } catch (Exception exception) {
                return new WrappedResponse<>(Response.Status.ERROR, null, exception);
            }
        }), listeningExecutorService);
    }

    @Override
    public Complete findSync(FindRequest<Complete> findModelRequest) throws Exception {
        return this.httpClient.executeRequestSync(
                modelMeta.getRouteKey() + "/" + findModelRequest.getId(),
                new CoreRequestCallable<>(this.modelMeta.getCompleteType(), mapper),
                new CoreRequestOptions(
                        RequestOptions.Type.GET,
                        new HashMap<>(),
                        "",
                        null
                )
        );
    }

    @Override
    public AsyncResponse<Complete> find(FindRequest<Complete> findModelRequest) {
        return new SimpleAsyncResponse<>(this.listeningExecutorService.submit(() -> {
            try {
                return new WrappedResponse<>(Response.Status.SUCCESS, findSync(findModelRequest), null);
            } catch (Exception exception) {
                return new WrappedResponse<>(Response.Status.ERROR, null, exception);
            }
        }), listeningExecutorService);
    }

    @Override
    public void deleteSync(DeleteRequest<Complete> deleteRequest) throws Exception {
        this.httpClient.executeRequestSync(
                this.modelMeta.getRouteKey() + "/" + deleteRequest.getId(),
                new CoreRequestCallable<>(null, this.mapper),
                new CoreRequestOptions(
                        RequestOptions.Type.DELETE,
                        new HashMap<>(),
                        null,
                        null
                )
        );
    }

    @Override
    public AsyncResponse<Void> delete(DeleteRequest<Complete> deleteRequest) {
        deleteRequest(deleteRequest.getId());
        return new SimpleAsyncResponse<>(this.listeningExecutorService.submit(() -> {
            try {
                return new WrappedResponse<>(Response.Status.SUCCESS, null, null);
            } catch (Exception exception) {
                return new WrappedResponse<>(Response.Status.ERROR, null, exception);
            }
        }), listeningExecutorService);
    }
}