package net.astrocube.commons.core.service;

import com.google.inject.Inject;
import net.astrocube.api.core.concurrent.*;
import net.astrocube.api.core.model.Model;
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

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;

public abstract class AsyncModelService<Complete extends Model, Partial extends PartialModel> implements
        CreateService<Complete, Partial>,
        DeleteService<Complete>,
        FindService<Complete>,
        PaginateService<Complete>,
        QueryService<Complete>,
        UpdateService<Complete, Partial>
{

    private @Inject ExecutorServiceProvider executorServiceProvider;
    private ExecutorService executorService;

    @Inject
    public void computeExecutorService() {
        this.executorService = executorServiceProvider.getRegisteredService();
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
