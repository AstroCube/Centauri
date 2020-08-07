package net.astrocube.api.core.service.find;

import com.google.common.reflect.TypeToken;
import net.astrocube.api.core.concurrent.AsyncResponse;
import net.astrocube.api.core.model.Model;

@SuppressWarnings("UnstableApiUsage")
public interface FindService<Complete extends Model> {

    /**
     * Complete type of the request
     * @return TypeToken with the complete type
     */
    TypeToken<Complete> getCompleteType();

    /**
     * Final request that will be passed to the API
     * @param id to be portrayed at the API
     * @return final request
     */
    default FindRequest<Complete> findRequest(String id) {
        return new FindRequest<Complete>() {
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
     * Final request that will be passed to the API
     * @param id to be portrayed at the API
     * @return final request
     */
    default Complete findSync(String id) throws Exception {
        return findSync(findRequest(id));
    }

    /**
     * Final request that will be passed to the API
     * @param findModelRequest to be passed
     * @return final request
     */
    Complete findSync(FindRequest<Complete> findModelRequest) throws Exception;

    /**
     * Final request that will be passed to the API
     * @param id to be portrayed at the API
     * @return final request
     */
    default AsyncResponse<Complete> find(String id) {
        return find(findRequest(id));
    }

    /**
     * Final request that will be passed to the API
     * @param findModelRequest to be passed
     * @return final request
     */
    AsyncResponse<Complete> find(FindRequest<Complete> findModelRequest);

}