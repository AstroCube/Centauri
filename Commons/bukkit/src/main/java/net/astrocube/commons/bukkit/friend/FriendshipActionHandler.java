package net.astrocube.commons.bukkit.friend;

import net.astrocube.api.bukkit.friend.FriendshipAction;
import net.astrocube.api.bukkit.friend.FriendshipActionEvent;
import net.astrocube.api.core.message.MessageListener;
import net.astrocube.api.core.message.MessageMetadata;
import org.bukkit.Bukkit;

public class FriendshipActionHandler implements MessageListener<FriendshipAction> {

	@Override
	public void handleDelivery(FriendshipAction message, MessageMetadata properties) {
		Bukkit.getPluginManager().callEvent(new FriendshipActionEvent(message));
	}

}
