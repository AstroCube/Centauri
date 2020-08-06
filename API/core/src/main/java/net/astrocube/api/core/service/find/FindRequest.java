package net.astrocube.api.core.service.find;

import net.astrocube.api.core.model.Model;
import net.astrocube.api.core.service.ModelRequest;

public interface FindRequest<Complete extends Model> extends ModelRequest<Complete> {

    /**
     * Id that will be portrayed for the final query
     * @return id
     */
    String getId();

}