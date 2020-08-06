package net.astrocube.commons.core.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.reflect.TypeParameter;
import com.google.common.reflect.TypeToken;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.astrocube.api.core.concurrent.AsyncResponse;
import net.astrocube.api.core.http.HttpClient;
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

@Singleton
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

    protected ModelMeta<Complete, Partial> modelMeta;
    protected TypeToken<QueryResult<Complete>> queryResultTypeToken;
    protected TypeToken<PaginateResult<Complete>> paginateResultTypeToken;

    public @Inject
    CoreModelService(ModelMeta<Complete, Partial> modelMeta) {
        this.modelMeta = modelMeta;
        this.queryResultTypeToken = new TypeToken<QueryResult<Complete>>(){}.where(new TypeParameter<Complete>(){}, this.modelMeta.getCompleteType());
        this.paginateResultTypeToken = new TypeToken<PaginateResult<Complete>>(){}.where(new TypeParameter<Complete>(){}, this.modelMeta.getCompleteType());
    }


    @Override
    public TypeToken<Complete> getCompleteType() {
        return null;
    }

    @Override
    public AsyncResponse<QueryResult<Complete>> query(QueryRequest<Complete> queryRequest) {
        return null;
    }

    @Override
    public QueryResult<Complete> querySync(QueryRequest<Complete> queryRequest) {
        return null;
    }

    @Override
    public TypeToken<Partial> getPartialType() {
        return null;
    }

    @Override
    public Complete updateSync(UpdateRequest<Partial> request) {
        return null;
    }

    @Override
    public AsyncResponse<Complete> update(UpdateRequest<Partial> request) {
        return null;
    }

    @Override
    public Complete createSync(CreateRequest<Partial> request) {
        return null;
    }

    @Override
    public AsyncResponse<Complete> create(CreateRequest<Partial> request) {
        return null;
    }

    @Override
    public TypeToken<Complete> completeType() {
        return null;
    }

    @Override
    public PaginateResult<Complete> paginateSync(PaginateRequest<Complete> paginateRequest) {
        return null;
    }

    @Override
    public AsyncResponse<PaginateResult<Complete>> paginate(PaginateRequest<Complete> paginateRequest) {
        return null;
    }

    @Override
    public Complete findSync(FindRequest<Complete> findModelRequest) {
        return null;
    }

    @Override
    public AsyncResponse<Complete> find(FindRequest<Complete> findModelRequest) {
        return null;
    }

    @Override
    public void deleteSync(DeleteRequest<Complete> deleteRequest) {

    }

    @Override
    public AsyncResponse<Void> delete(DeleteRequest<Complete> deleteRequest) {
        return null;
    }
}