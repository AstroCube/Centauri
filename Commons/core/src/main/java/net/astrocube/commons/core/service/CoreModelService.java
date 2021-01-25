package net.astrocube.commons.core.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.reflect.TypeToken;
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
import net.astrocube.api.core.service.paginate.PaginateBaseResult;
import net.astrocube.api.core.service.paginate.PaginateRequest;
import net.astrocube.api.core.service.paginate.PaginateResult;
import net.astrocube.api.core.service.paginate.PaginateService;
import net.astrocube.api.core.service.query.QueryBaseResult;
import net.astrocube.api.core.service.query.QueryRequest;
import net.astrocube.api.core.service.query.QueryResult;
import net.astrocube.api.core.service.query.QueryService;
import net.astrocube.api.core.service.update.UpdateRequest;
import net.astrocube.api.core.service.update.UpdateService;
import net.astrocube.commons.core.http.CoreRequestCallable;
import net.astrocube.commons.core.http.CoreRequestOptions;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;

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
    protected final ObjectMapper mapper;
    private final ExecutorService executorService;

    protected ModelMeta<Complete, Partial> modelMeta;
    protected JavaType queryResultTypeToken;
    protected JavaType paginateResultTypeToken;

    public @Inject
    CoreModelService(ObjectMapper mapper, ModelMeta<Complete, Partial> modelMeta, ExecutorServiceProvider executorServiceProvider) {
        this.modelMeta = modelMeta;
        this.executorService = executorServiceProvider.getRegisteredService();
        this.mapper = mapper;
        this.queryResultTypeToken = mapper.getTypeFactory().constructParametricType(QueryResult.class, modelMeta.getCompleteType());
        this.paginateResultTypeToken = mapper.getTypeFactory().constructParametricType(PaginateResult.class, modelMeta.getCompleteType());
    }

    @Override
    public JavaType getCompleteType() {
        return modelMeta.getCompleteType();
    }

    @Override
    public AsyncResponse<QueryResult<Complete>> query(QueryRequest<Complete> queryRequest) {
        return new SimpleAsyncResponse<>(CompletableFuture.supplyAsync(() -> {
            try {
                return new WrappedResponse<>(Response.Status.SUCCESS, querySync(queryRequest), null);
            } catch (Exception exception) {
                return new WrappedResponse<>(Response.Status.ERROR, null, exception);
           }
        }, executorService));
    }

    @Override
    public QueryResult<Complete> querySync(QueryRequest<Complete> queryRequest) throws Exception {

        QueryBaseResult queryResult = this.httpClient.executeRequestSync(
                modelMeta.getRouteKey() + "/list",
                new CoreRequestCallable<>(TypeToken.of(QueryBaseResult.class), mapper),
                new CoreRequestOptions(
                        RequestOptions.Type.POST,
                        new HashMap<>(),
                        queryRequest.getBsonQuery() == null ? "{}"
                                : this.mapper.writeValueAsString(queryRequest.getBsonQuery()),
                        "?page=-1"
                )
        );

        return () -> {
            try {
                return mapper.readValue(
                        queryResult.getFoundModels().toString(),
                        mapper.getTypeFactory().constructParametricType(Set.class, getCompleteType())
                );
            } catch (JsonProcessingException e) {
                return new HashSet<>();
            }
        };

    }

    @Override
    public JavaType getPartialType() {
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
        return new SimpleAsyncResponse<>(CompletableFuture.supplyAsync(() -> {
            try {
                return new WrappedResponse<>(Response.Status.SUCCESS, updateSync(request), null);
            } catch (Exception exception) {
                return new WrappedResponse<>(Response.Status.ERROR, null, exception);
            }
        }, executorService));
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
        return new SimpleAsyncResponse<>(CompletableFuture.supplyAsync(() -> {
            try {
                return new WrappedResponse<>(Response.Status.SUCCESS, createSync(request), null);
            } catch (Exception exception) {
                return new WrappedResponse<>(Response.Status.ERROR, null, exception);
            }
        }, executorService));
    }

    @Override
    public PaginateResult<Complete> paginateSync(PaginateRequest<Complete> paginateRequest) throws Exception {
        PaginateBaseResult paginateResult = this.httpClient.executeRequestSync(
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

        return new PaginateResult<Complete>() {
            @Override
            public Set<Complete> getData() {
                try {
                    return mapper.readValue(
                            paginateResult.getData().toString(),
                            mapper.getTypeFactory().constructParametricType(Set.class, getCompleteType())
                    );
                } catch (JsonProcessingException e) {
                    return new HashSet<>();
                }
            }

            @Override
            public Pagination getPagination() {
                return new Pagination() {
                    @Override
                    public int perPage() {
                        return paginateResult.getPagination().perPage();
                    }

                    @Override
                    public boolean hasPrevPage() {
                        return paginateResult.getPagination().hasPrevPage();
                    }

                    @Override
                    public boolean hasNextPage() {
                        return paginateResult.getPagination().hasNextPage();
                    }

                    @Override
                    public Optional<Integer> prevPage() {
                        return paginateResult.getPagination().prevPage();
                    }

                    @Override
                    public Optional<Integer> nextPage() {
                        return paginateResult.getPagination().nextPage();
                    }

                    @Override
                    public Optional<Integer> page() {
                        return paginateResult.getPagination().page();
                    }

                    @Override
                    public Optional<Integer> totalPages() {
                        return paginateResult.getPagination().totalPages();
                    }
                };
            }
        };
    }

    @Override
    public AsyncResponse<PaginateResult<Complete>> paginate(PaginateRequest<Complete> paginateRequest) {
        return new SimpleAsyncResponse<>(CompletableFuture.supplyAsync(() -> {
            try {
                return new WrappedResponse<>(Response.Status.SUCCESS,paginateSync(paginateRequest), null);
            } catch (Exception exception) {
                return new WrappedResponse<>(Response.Status.ERROR, null, exception);
            }
        }, executorService));
    }

    @Override
    public Complete findSync(FindRequest<Complete> findModelRequest) throws Exception {
        return this.httpClient.executeRequestSync(
                modelMeta.getRouteKey() + "/" + findModelRequest.getId(),
                new CoreRequestCallable<>(getCompleteType(), mapper),
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
        return new SimpleAsyncResponse<>(CompletableFuture.supplyAsync(() -> {
            try {
                return new WrappedResponse<>(Response.Status.SUCCESS, findSync(findModelRequest), null);
            } catch (Exception exception) {
                return new WrappedResponse<>(Response.Status.ERROR, null, exception);
            }
        }, executorService));
    }

    @Override
    public void deleteSync(DeleteRequest<Complete> deleteRequest) throws Exception {
        this.httpClient.executeRequestSync(
                this.modelMeta.getRouteKey() + "/" + deleteRequest.getId(),
                new CoreRequestCallable<>(TypeToken.of(Void.class), this.mapper),
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
        return new SimpleAsyncResponse<>(CompletableFuture.supplyAsync(() -> {
            try {
                this.deleteSync(deleteRequest);
                return new WrappedResponse<>(Response.Status.SUCCESS, null, null);
            } catch (Exception exception) {
                return new WrappedResponse<>(Response.Status.ERROR, null, exception);
            }
        }, executorService));
    }
}