package net.astrocube.commons.bukkit.listener.friend;

import com.google.inject.Inject;
import me.yushust.message.MessageHandler;
import net.astrocube.api.bukkit.friend.FriendshipAction;
import net.astrocube.api.bukkit.friend.FriendshipActionEvent;
import net.astrocube.api.bukkit.translation.mode.AlertModes;
import net.astrocube.api.bukkit.user.display.DisplayMatcher;
import net.astrocube.api.bukkit.user.display.TranslatedFlairFormat;
import net.astrocube.api.core.service.find.FindService;
import net.astrocube.api.core.virtual.friend.FriendshipDoc;
import net.astrocube.api.core.virtual.user.User;
import net.astrocube.commons.bukkit.command.friends.FriendCallbackHelper;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class FriendForceListener implements Listener {

	private @Inject MessageHandler messageHandler;
	private @Inject FindService<User> findService;
	private @Inject DisplayMatcher matcher;
	private @Inject FriendCallbackHelper friendCallbackHelper;

	@EventHandler
	public void onFriendshipAction(FriendshipActionEvent event) {

		if (event.getAction().getActionType() != FriendshipAction.Action.FORCE) {
			return;
		}

		FriendshipDoc.Creation friendship = event.getAction().getFriendship();

		Player issuer = Bukkit.getPlayerByDatabaseId(friendship.getIssuer());
		Player receiver = Bukkit.getPlayerByDatabaseId(friendship.getReceiver());
		Player sender = Bukkit.getPlayerByDatabaseId(friendship.getSender());

		if (issuer != null) {
			alertForced(issuer.getPlayer(), friendship);
		}

		if (receiver != null) {
			alertForced(receiver.getPlayer(), friendship);
		}

		if (sender != null) {
			alertForced(sender.getPlayer(), friendship);
		}

	}

	private void alertForced(Player player, FriendshipDoc.Creation relation) {

		boolean simple = relation.getSender().equalsIgnoreCase(relation.getIssuer());

		if (simple) {

			boolean sender = player.getDatabaseId().equalsIgnoreCase(relation.getIssuer());

			String target = sender ?
				relation.getSender() : relation.getReceiver();

			findService.find(target).callback(targetResponse ->
				targetResponse.ifSuccessful(targetUser -> {

					TranslatedFlairFormat format = matcher.getDisplay(player, targetUser);

					if (sender) {
						messageHandler.sendReplacingIn(
							player, AlertModes.MUTED, "friend.request.forced.simple.sender",
							"%player%", format.getColor() + targetUser.getDisplay()
						);
					} else {
						messageHandler.sendReplacingIn(
							player, AlertModes.MUTED, "friend.request.forced.simple.target",
							"%player%", format.getColor() + targetUser.getDisplay()
						);
					}

				})
			);

		} else {

			if (player.getDatabaseId().equalsIgnoreCase(relation.getIssuer())) {
				sendMultiForceMessage(
					player,
					relation.getSender(),
					relation.getIssuer(),
					"friend.request.forced.multi.sender"
				);
			} else {
				sendMultiForceMessage(
					player,
					relation.getIssuer(),
					player.getDatabaseId().equalsIgnoreCase(relation.getSender()) ?
						relation.getIssuer() : relation.getSender(),
					relation.isAlerted() ? "friend.request.forced.multi.target" : "friend.request.forced.multi.silent"
				);
			}

		}


	}

	private void sendMultiForceMessage(Player player, String firstId, String secondId, String path) {
		friendCallbackHelper.findUserByName(firstId, (firstException, first) ->
			friendCallbackHelper.findUserByName(secondId, (secondException, second) -> {

				if (first.isPresent() && second.isPresent()) {

					TranslatedFlairFormat formatFirst = matcher.getDisplay(player, first.get());
					TranslatedFlairFormat formatSecond = matcher.getDisplay(player, second.get());


					messageHandler.sendReplacingIn(
						player, AlertModes.INFO, path,
						"%player%", formatFirst.getColor() + first.get().getDisplay(),
						"%second%", formatSecond.getColor() + second.get().getDisplay()
					);

				}

			})
		);
	}

}
