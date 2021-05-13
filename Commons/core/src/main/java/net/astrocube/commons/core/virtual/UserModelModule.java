package net.astrocube.commons.core.virtual;

import com.google.inject.TypeLiteral;
import me.fixeddev.inject.ProtectedModule;
import net.astrocube.api.core.utils.ResolvableType;
import net.astrocube.api.core.virtual.user.User;
import net.astrocube.api.core.virtual.user.UserDoc;
import net.astrocube.commons.core.model.binder.ModelBinderModule;
import net.astrocube.commons.core.service.RedisModelService;

public class UserModelModule extends ProtectedModule implements ModelBinderModule {

	@Override
	protected void configure() {
		bindModel(User.class, UserDoc.Partial.class, model -> {
			TypeLiteral<RedisModelService<User, UserDoc.Partial>> serviceTypeLiteral =
				new ResolvableType<RedisModelService<User, UserDoc.Partial>>() {
				};
			model.bind(serviceTypeLiteral);
		});
	}

}
