package net.astrocube.commons.core.virtual;

import com.google.inject.TypeLiteral;
import me.fixeddev.inject.ProtectedModule;
import net.astrocube.commons.core.model.binder.ModelBinderModule;
import net.astrocube.api.core.utils.ResolvableType;
import net.astrocube.api.core.virtual.friend.Friendship;
import net.astrocube.api.core.virtual.friend.FriendshipDoc;
import net.astrocube.commons.core.service.CoreModelService;

public class FriendshipModelModule extends ProtectedModule implements ModelBinderModule {

    @Override
    protected void configure() {
        bindModel(Friendship.class, FriendshipDoc.Partial.class, model -> {
            TypeLiteral<CoreModelService<Friendship, FriendshipDoc.Partial>> serviceTypeLiteral =
                    new ResolvableType<CoreModelService<Friendship, FriendshipDoc.Partial>>(){};
            model.bind(serviceTypeLiteral);
        });
    }

}