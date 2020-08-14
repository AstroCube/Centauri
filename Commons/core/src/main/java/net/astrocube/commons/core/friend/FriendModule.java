package net.astrocube.commons.core.friend;

import net.astrocube.api.core.friend.FriendshipHandler;
import net.astrocube.commons.core.inject.ProtectedModule;

public class FriendModule extends ProtectedModule {

    @Override
    protected void configure() {
        bind(FriendshipHandler.class).to(CoreFriendshipHandler.class);
        expose(FriendshipHandler.class);
    }

}
