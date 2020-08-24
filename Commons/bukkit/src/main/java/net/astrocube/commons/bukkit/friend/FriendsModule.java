package net.astrocube.commons.bukkit.friend;

import me.fixeddev.inject.ProtectedModule;
import net.astrocube.api.bukkit.friend.FriendHelper;

public class FriendsModule extends ProtectedModule {

    @Override
    public void configure() {
        bind(FriendHelper.class).to(CoreFriendHelper.class);
        expose(FriendHelper.class);
    }
}
