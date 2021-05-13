package net.astrocube.commons.core.virtual;

import com.google.inject.TypeLiteral;
import me.fixeddev.inject.ProtectedModule;
import net.astrocube.api.core.utils.ResolvableType;
import net.astrocube.api.core.virtual.group.Group;
import net.astrocube.commons.core.model.binder.ModelBinderModule;
import net.astrocube.commons.core.service.RedisModelService;

public class GroupModelModule extends ProtectedModule implements ModelBinderModule {

	@Override
	protected void configure() {
		bindModel(Group.class, model -> {
			TypeLiteral<RedisModelService<Group, Group>> serviceTypeLiteral =
				new ResolvableType<RedisModelService<Group, Group>>() {
				};
			model.bind(serviceTypeLiteral);
		});
	}
}