package net.astrocube.commons.core.virtual;

import com.google.inject.TypeLiteral;
import me.fixeddev.inject.ProtectedModule;
import net.astrocube.api.core.virtual.user.User;
import net.astrocube.api.core.virtual.user.UserDoc;
import net.astrocube.commons.core.model.binder.ModelBinder;
import net.astrocube.commons.core.service.RedisModelService;

public class UserModelModule extends ProtectedModule implements ModelBinder {

	@Override
	protected void configure() {
		bindModel(User.class, UserDoc.Partial.class)
			.toSingleService(new TypeLiteral<RedisModelService<User, UserDoc.Partial>>() {});
	}

}
