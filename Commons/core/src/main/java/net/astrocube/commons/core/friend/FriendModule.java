package net.astrocube.commons.core.friend;

import me.fixeddev.inject.ProtectedModule;
import net.astrocube.api.core.friend.FriendshipHandler;

public class FriendModule extends ProtectedModule {

    @Override
    protected void configure() {
        bind(FriendshipHandler.class).to(CoreFriendshipHandler.class);
        expose(FriendshipHandler.class);
    }

}
