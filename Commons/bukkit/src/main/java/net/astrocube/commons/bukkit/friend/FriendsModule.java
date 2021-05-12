package net.astrocube.commons.bukkit.friend;

import me.fixeddev.inject.ProtectedModule;
import net.astrocube.api.bukkit.friend.FriendHelper;
import net.astrocube.api.bukkit.friend.FriendshipAction;
import net.astrocube.api.core.friend.FriendshipHandler;
import net.astrocube.api.core.message.ChannelBinder;

public class FriendsModule extends ProtectedModule implements ChannelBinder {

	@Override
	public void configure() {
		bind(FriendHelper.class).to(CoreFriendHelper.class);
		bind(FriendshipHandler.class).to(CoreFriendshipHandler.class);
		bindChannel(FriendshipAction.class)
			.registerHandler(new FriendshipActionHandler());
		expose(FriendshipHandler.class);
		expose(FriendHelper.class);
	}
}
