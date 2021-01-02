package net.astrocube.commons.bukkit.friend;

import me.fixeddev.inject.ProtectedModule;
import net.astrocube.api.bukkit.friend.FriendHelper;
import net.astrocube.api.core.friend.FriendshipHandler;

public class FriendsModule extends ProtectedModule {

    @Override
    public void configure() {
        bind(FriendHelper.class).to(CoreFriendHelper.class);
        bind(FriendshipHandler.class).to(CoreFriendshipHandler.class);
        expose(FriendshipHandler.class);
        expose(FriendHelper.class);
    }
}
