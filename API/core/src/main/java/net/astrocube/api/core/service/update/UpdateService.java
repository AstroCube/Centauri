package net.astrocube.api.core.service.update;

import com.fasterxml.jackson.databind.JavaType;
import net.astrocube.api.core.concurrent.AsyncResponse;
import net.astrocube.api.core.model.Model;
import net.astrocube.api.core.model.PartialModel;

@SuppressWarnings("UnstableApiUsage")
public interface UpdateService<Complete extends Model, Partial extends PartialModel> {

    /**
     * Will return type of the full model
     * @return TypeToken of complete
     */
    JavaType getCompleteType();

    /**
     * Will return type of the partial model
     * @return TypeToken of partial
     */
    JavaType getPartialType();

    /**
     * Default update request to be called from service
     * @param partial that will be updated
     * @return update request
     */
    default UpdateRequest<Partial> updateRequest(Partial partial) {
        return () -> partial;
    }

    /**
     * Update request to be called from service
     * @param partial that will be updated
     * @return update request
     */
    default Complete updateSync(Partial partial) throws Exception {
        return updateSync(updateRequest(partial));
    }

    /**
     * Request to be called after update
     * @param request that will be sent for update
     * @return updated request
     */
    Complete updateSync(UpdateRequest<Partial> request) throws Exception;

    /**
     * Update request to be called from service
     * @param partial that will be updated
     * @return update request
     */
    default AsyncResponse<Complete> update(Partial partial) {
        return update(updateRequest(partial));
    }

    /**
     * Request to be called after update
     * @param request that will be sent for update
     * @return updated request
     */
    AsyncResponse<Complete> update(UpdateRequest<Partial> request);

}