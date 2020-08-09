package net.astrocube.api.core.service.paginate;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.reflect.TypeToken;
import net.astrocube.api.core.concurrent.AsyncResponse;
import net.astrocube.api.core.model.Model;

@SuppressWarnings("UnstableApiUsage")
public interface PaginateService<Complete extends Model> {

    /**
     * Complete type of the requested service
     * @return TypeToken of the query
     */
    TypeToken<Complete> getCompleteType();


    /**
     * Paginated query that will be called from the service
     * @param paginateQuery to append at the link
     * @param bsonQuery to be used as body
     * @return paginated request
     */
    default PaginateRequest<Complete> paginateRequest(String paginateQuery, ObjectNode bsonQuery) {
        return new PaginateRequest<Complete>() {
            @Override
            public String getPaginateQuery() {
                return paginateQuery;
            }

            @Override
            public ObjectNode getBsonQuery() {
                return bsonQuery;
            }

            public TypeToken<Complete> getModelType() {
                return getCompleteType();
            }
        };
    }

    /**
     * Paginated query that will be called from the service
     * @param paginateQuery to append at the link
     * @param bsonQuery to be used as body
     * @return paginated request
     */
    default PaginateResult<Complete> paginateSync(String paginateQuery, ObjectNode bsonQuery) throws Exception {
        return paginateSync(paginateRequest(paginateQuery, bsonQuery));
    }

    /**
     * Paginated query that will be called from the service
     * @param paginateRequest to be used
     * @return paginated request
     */
    PaginateResult<Complete> paginateSync(PaginateRequest<Complete> paginateRequest) throws Exception;

    /**
     * Paginated query that will be called from the service
     * @param paginateQuery to append at the link
     * @param bsonQuery to be used as body
     * @return paginated request
     */
    default AsyncResponse<PaginateResult<Complete>> paginate(String paginateQuery, ObjectNode bsonQuery) {
        return paginate(paginateRequest(paginateQuery, bsonQuery));
    }

    /**
     * Paginated query that will be called from the service
     * @param paginateRequest to be used
     * @return paginated request
     */
    AsyncResponse<PaginateResult<Complete>> paginate(PaginateRequest<Complete> paginateRequest);

}