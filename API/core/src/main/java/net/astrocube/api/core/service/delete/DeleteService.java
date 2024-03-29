package net.astrocube.api.core.service.delete;

import com.google.common.reflect.TypeToken;
import net.astrocube.api.core.concurrent.AsyncResponse;
import net.astrocube.api.core.model.Model;

@SuppressWarnings("UnstableApiUsage")
public interface DeleteService<Complete extends Model> {

    /**
     * Will return type of the full model
     * @return TypeToken of complete
     */
    TypeToken<Complete> getCompleteType();

    /**
     * Default delete request to be called from service
     * @param id of item taht will be deleted
     * @return update request
     */
    default DeleteRequest<Complete> deleteRequest(String id) {
        return new DeleteRequest<Complete>() {
            @Override
            public String getId() {
                return id;
            }

            public TypeToken<Complete> getModelType() {
                return getCompleteType();
            }
        };
    }

    /**
     * Default delete request to be called from service
     * @param id of item taht will be deleted
     * @return update request
     */
    default void deleteSync(String id) throws Exception {
        deleteSync(deleteRequest(id));
    }

    /**
     * Delete final switch
     * @param deleteRequest that will be passed
     */
    void deleteSync(DeleteRequest<Complete> deleteRequest) throws Exception;

    /**
     * Default delete request to be called from service
     * @param id of item taht will be deleted
     * @return update request
     */
    default AsyncResponse<Void> delete(String id) {
        return delete(deleteRequest(id));
    }

    /**
     * Delete final switch
     * @param deleteRequest that will be passed
     */
    AsyncResponse<Void> delete(DeleteRequest<Complete> deleteRequest);

}