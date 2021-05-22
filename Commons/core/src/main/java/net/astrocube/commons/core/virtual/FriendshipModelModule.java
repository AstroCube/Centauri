package net.astrocube.commons.core.virtual;

import com.google.inject.TypeLiteral;
import me.fixeddev.inject.ProtectedModule;
import net.astrocube.api.core.virtual.friend.Friendship;
import net.astrocube.api.core.virtual.friend.FriendshipDoc;
import net.astrocube.commons.core.model.binder.ModelBinder;
import net.astrocube.commons.core.service.CoreModelService;

public class FriendshipModelModule extends ProtectedModule implements ModelBinder {

	@Override
	protected void configure() {
		bindModel(Friendship.class, FriendshipDoc.Partial.class)
			.toSingleService(new TypeLiteral<CoreModelService<Friendship, FriendshipDoc.Partial>>() {});
	}

}