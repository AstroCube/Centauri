package net.astrocube.api.core.service.create;

import com.google.common.reflect.TypeToken;
import net.astrocube.api.core.concurrent.AsyncResponse;
import net.astrocube.api.core.model.Model;
import net.astrocube.api.core.model.PartialModel;

@SuppressWarnings("UnstableApiUsage")
public interface CreateService<Complete extends Model, Partial extends PartialModel> {

    /**
     * Will return type of the full model
     * @return TypeToken of complete
     */
    TypeToken<Complete> getCompleteType();

    /**
     * Will return type of the partial model
     * @return TypeToken of partial
     */
    TypeToken<Partial> getPartialType();

    /**
     * Default create request to be called from service
     * @param partial that will be created
     * @return update request
     */
    default CreateRequest<Partial> createRequest(Partial partial) {
        return new CreateRequest<Partial>() {
            @Override
            public Partial getModel() {
                return partial;
            }

            public TypeToken<Partial> getModelType() {
                return getPartialType();
            }
        };
    }

    /**
     * Default create request to be called from service
     * @param model that will be created
     * @return update request
     */
    default Complete createSync(Partial model) {
        return createSync(createRequest(model));
    }

    /**
     * Request to be called after creation
     * @param request that will be sent for creation
     * @return created and serialized object
     */
    Complete createSync(CreateRequest<Partial> request);

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