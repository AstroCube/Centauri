package net.astrocube.api.core.service.query;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.reflect.TypeToken;
import net.astrocube.api.core.concurrent.AsyncResponse;
import net.astrocube.api.core.model.Model;

import javax.annotation.Nullable;

@SuppressWarnings("UnstableApiUsage")
public interface QueryService<Complete extends Model> {

    /**
     * Will return type of the full model
     * @return TypeToken of complete
     */
    TypeToken<Complete> getCompleteType();

    /**
     * Query that will be called from the service
     * @param bsonQuery that will be serialized
     * @return query request
     */
    default QueryRequest<Complete> queryRequest(@Nullable ObjectNode bsonQuery) {
        return new QueryRequest<Complete>() {
            @Override
            public @Nullable ObjectNode getBsonQuery() {
                return bsonQuery;
            }

            public TypeToken<Complete> getModelType() {
                return getCompleteType();
            }
        };
    }

    /**
     * Query that will return all values from service
     * @return result with requested values
     */
    default AsyncResponse<QueryResult<Complete>> getAll() {
        return query(queryRequest(null));
    }

    /**
     * Query that will be called from the service
     * @param bsonQuery that will be serialized
     * @return query request
     */
    default AsyncResponse<QueryResult<Complete>> query(ObjectNode bsonQuery) {
        return query(queryRequest(bsonQuery));
    }



    /**
     * Request to be called after update
     * @param queryRequest that will be sent for update
     * @return updated request
     */
    AsyncResponse<QueryResult<Complete>> query(QueryRequest<Complete> queryRequest);

    /**
     * Query that will be called from the service
     * @param bsonQuery that will be serialized
     * @return query request
     */
    default QueryResult<Complete> querySync(ObjectNode bsonQuery) throws Exception {
        return querySync(queryRequest(bsonQuery));
    }

    /**
     * Request to be called after update
     * @param queryRequest that will be sent for update
     * @return updated request
     */
    QueryResult<Complete> querySync(QueryRequest<Complete> queryRequest) throws Exception;

}