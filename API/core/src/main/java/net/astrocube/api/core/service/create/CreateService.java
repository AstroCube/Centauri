package net.astrocube.api.core.service.create;

import com.fasterxml.jackson.databind.JavaType;
import net.astrocube.api.core.concurrent.AsyncResponse;
import net.astrocube.api.core.model.Model;
import net.astrocube.api.core.model.PartialModel;

@SuppressWarnings("UnstableApiUsage")
public interface CreateService<Complete extends Model, Partial extends PartialModel> {

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
     * Default create request to be called from service
     * @param partial that will be created
     * @return update request
     */
    default CreateRequest<Partial> createRequest(Partial partial) {
        return () -> partial;
    }

    /**
     * Default create request to be called from service
     * @param model that will be created
     * @return update request
     */
    default Complete createSync(Partial model) throws Exception {
        return createSync(createRequest(model));
    }

    /**
     * Request to be called after creation
     * @param request that will be sent for creation
     * @return created and serialized object
     */
    Complete createSync(CreateRequest<Partial> request) throws Exception;

    /**
     * Default create request to be called from service
     * @param model that will be created
     * @return update request
     */
    default AsyncResponse<Complete> create(Partial model) {
        return create(createRequest(model));
    }

    /**
     * Request to be called after creation
     * @param request that will be sent for creation
     * @return created and serialized object
     */
    AsyncResponse<Complete> create(CreateRequest<Partial> request);

}