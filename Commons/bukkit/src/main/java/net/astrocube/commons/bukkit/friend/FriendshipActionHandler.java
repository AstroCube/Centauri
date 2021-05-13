package net.astrocube.commons.bukkit.friend;

import net.astrocube.api.bukkit.friend.FriendshipAction;
import net.astrocube.api.bukkit.friend.FriendshipActionEvent;
import net.astrocube.api.core.message.MessageHandler;
import net.astrocube.api.core.message.Metadata;
import org.bukkit.Bukkit;

public class FriendshipActionHandler implements MessageHandler<FriendshipAction> {

	@Override
	public Class<FriendshipAction> type() {
		return FriendshipAction.class;
	}

	@Override
	public void handleDelivery(FriendshipAction message, Metadata properties) {
		Bukkit.getPluginManager().callEvent(new FriendshipActionEvent(message));
	}

}
