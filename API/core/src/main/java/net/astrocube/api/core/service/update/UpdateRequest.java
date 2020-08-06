package net.astrocube.api.core.service.update;

import net.astrocube.api.core.model.PartialModel;
import net.astrocube.api.core.service.ModelRequest;

public interface UpdateRequest<Partial extends PartialModel> extends ModelRequest<Partial> {

    /**
     * Model of the request to be serialized
     * @return partial
     */
    Partial getModel();

}