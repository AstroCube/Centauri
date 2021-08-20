package net.astrocube.commons.core.virtual;

import com.google.inject.TypeLiteral;
import net.astrocube.inject.ProtectedModule;
import net.astrocube.api.core.virtual.group.Group;
import net.astrocube.commons.core.model.binder.ModelBinder;
import net.astrocube.commons.core.service.RedisModelService;

public class GroupModelModule extends ProtectedModule implements ModelBinder {

	@Override
	protected void configure() {
		bindModel(Group.class)
			.toSingleService(new TypeLiteral<RedisModelService<Group, Group>>() {});
	}
}