package net.astrocube.commons.bukkit.listener.friend;

import com.google.inject.Inject;
import me.yushust.message.MessageHandler;
import me.yushust.message.util.StringList;
import net.astrocube.api.bukkit.friend.FriendshipAction;
import net.astrocube.api.bukkit.friend.FriendshipActionEvent;
import net.astrocube.api.bukkit.translation.mode.AlertModes;
import net.astrocube.api.bukkit.user.display.DisplayMatcher;
import net.astrocube.api.bukkit.user.display.TranslatedFlairFormat;
import net.astrocube.api.core.service.find.FindService;
import net.astrocube.api.core.virtual.friend.FriendshipDoc;
import net.astrocube.api.core.virtual.user.User;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class FriendExpireListener implements Listener {

	private @Inject DisplayMatcher displayMatcher;
	private @Inject MessageHandler messageHandler;
	private @Inject FindService<User> findService;

	@EventHandler
	public void onFriendshipAction(FriendshipActionEvent event) {

		if (event.getAction().getActionType() != FriendshipAction.Action.EXPIRE) {
			return;
		}

		FriendshipDoc.Relation friendship = event.getAction().getFriendship();

		Player player = Bukkit.getPlayerByDatabaseId(friendship.getSender());

		if (player == null) {
			return;
		}

		findService.find(event.getAction().getFriendship().getReceiver()).callback(receiverCallback ->
			receiverCallback.ifSuccessful(receiver -> {
				TranslatedFlairFormat flairFormat = displayMatcher.getDisplay(player, receiver);
				messageHandler.sendReplacingIn(
					player, AlertModes.MUTED, "friend.request.expired",
					"%receiver%", flairFormat.getColor() + receiver.getDisplay()
				);
			})
		);


	}

}
