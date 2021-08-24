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

public class FriendAddListener implements Listener {

	private @Inject DisplayMatcher displayMatcher;
	private @Inject MessageHandler messageHandler;
	private @Inject FindService<User> findService;

	@EventHandler
	public void onFriendshipAction(FriendshipActionEvent event) {

		if (event.getAction().getActionType() != FriendshipAction.Action.ADD) {
			return;
		}

		FriendshipDoc.Relation friendship = event.getAction().getFriendship();

		Player receiver = Bukkit.getPlayerByDatabaseId(friendship.getReceiver());

		if (receiver == null) {
			return;
		}

		findService.find(friendship.getSender()).callback(senderResponse ->
			senderResponse.ifSuccessful(sender -> {

				TranslatedFlairFormat flairFormat = displayMatcher.getDisplay(receiver, sender);

				BaseComponent[] builder = new ComponentBuilder(
					messageHandler.get(receiver, "friend.request.holder")
				)
					.event(new ClickEvent(
						ClickEvent.Action.RUN_COMMAND, "/friends accept " + sender.getUsername())
					)
					.event(
						new HoverEvent(
							HoverEvent.Action.SHOW_TEXT,
							new ComponentBuilder(
								messageHandler.get(receiver, "friend.request.holder")
							)
								.color(ChatColor.RED)
								.bold(true)
								.create()
						)
					)
					.color(ChatColor.RED)
					.bold(true)
					.create();


				StringList list = messageHandler.replacingMany(
					receiver, "friend.request.received",
					"%sender%", flairFormat.getColor() + sender.getDisplay()
				);

				receiver.playSound(receiver.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1f, 1f);
				list.forEach(component -> {

					if (component.equalsIgnoreCase("%holder%")) {
						receiver.spigot().sendMessage(builder);
					} else {
						receiver.sendMessage(component);
					}

				});

			})
		);

	}

}
