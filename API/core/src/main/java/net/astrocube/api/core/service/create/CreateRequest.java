package net.astrocube.api.core.service.create;

import net.astrocube.api.core.model.PartialModel;
import net.astrocube.api.core.service.ModelRequest;

public interface CreateRequest<Partial extends PartialModel> extends ModelRequest<Partial> {

	/**
	 * Model of the request to be serialized
	 * @return partial
	 */
	Partial getModel();

}