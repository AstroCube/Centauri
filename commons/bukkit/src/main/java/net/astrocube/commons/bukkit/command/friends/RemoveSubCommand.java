package net.astrocube.commons.bukkit.command.friends;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.inject.Inject;
import me.fixeddev.commandflow.annotated.CommandClass;
import me.fixeddev.commandflow.annotated.annotation.Command;
import me.fixeddev.commandflow.bukkit.annotation.Sender;
import me.yushust.message.MessageHandler;
import net.astrocube.api.bukkit.friend.FriendHelper;
import net.astrocube.api.bukkit.translation.mode.AlertModes;
import net.astrocube.api.bukkit.user.display.DisplayMatcher;
import net.astrocube.api.bukkit.user.display.TranslatedFlairFormat;
import net.astrocube.api.core.service.delete.DeleteService;
import net.astrocube.api.core.service.query.QueryService;
import net.astrocube.api.core.virtual.friend.Friendship;
import net.astrocube.commons.bukkit.utils.UserUtils;
import net.astrocube.commons.core.utils.Callbacks;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

@Command(names = {"remove", "rm", "delete", "del"})
public class RemoveSubCommand implements CommandClass {

	private @Inject FriendCallbackHelper friendCallbackHelper;
	private @Inject FriendHelper friendHelper;
	private @Inject DeleteService<Friendship> deleteService;
	private @Inject DisplayMatcher displayMatcher;
	private @Inject QueryService<Friendship> queryService;
	private @Inject ObjectMapper objectMapper;
	private @Inject MessageHandler messageHandler;

	@Command(names = "")
	public boolean execute(@Sender Player player, OfflinePlayer target) {

		if (UserUtils.checkSamePlayer(player, target, messageHandler)) {
			return true;
		}

		friendCallbackHelper.findUsers(player, target, (user, targetUser) -> {

			if (friendHelper.checkNotFriends(player, user, targetUser)) {
				return;
			}

			ObjectNode node = objectMapper.createObjectNode();
			node.putArray("$or")
				.add(
					objectMapper.createObjectNode()
						.put("issuer", user.getId())
						.put("receiver", targetUser.getId())
				)
				.add(
					objectMapper.createObjectNode()
						.put("issuer", targetUser.getId())
						.put("receiver", user.getId())
				);

			queryService.query(node).callback(queryResponse -> {

				if (!queryResponse.isSuccessful()) {
					messageHandler.sendIn(player, AlertModes.ERROR, "friend.error.internal");
				}

				queryResponse.ifSuccessful(friendships ->
					deleteService.delete(
						friendships.getFoundModels()
							.iterator()
							.next()
							.getId()
					).callback(deleted -> {

						if (!deleted.isSuccessful()) {
							messageHandler.sendIn(player, AlertModes.ERROR, "friend.error.internal");
						}

						deleted.ifSuccessful(response -> {

							TranslatedFlairFormat flairFormat = displayMatcher.getDisplay(
								player,
								targetUser
							);

							messageHandler.sendReplacingIn(
								player, AlertModes.MUTED, "friend.request.removed",
								"%player%", flairFormat.getColor() + targetUser.getDisplay()
							);

						});

					})
				);

			});

		});

		return true;
	}

}