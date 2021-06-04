package net.astrocube.api.core.service.delete;

import net.astrocube.api.core.model.Model;
import net.astrocube.api.core.service.ModelRequest;

import javax.annotation.Nullable;

public interface DeleteRequest<Complete extends Model> extends ModelRequest<Complete> {

	@Nullable
	String getId();

}