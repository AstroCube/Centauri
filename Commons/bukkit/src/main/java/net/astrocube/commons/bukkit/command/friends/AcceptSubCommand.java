package net.astrocube.commons.bukkit.command.friends;

import com.google.inject.Inject;
import me.fixeddev.commandflow.annotated.CommandClass;
import me.fixeddev.commandflow.annotated.annotation.Command;
import me.fixeddev.commandflow.bukkit.annotation.Sender;
import me.yushust.message.MessageHandler;
import net.astrocube.api.bukkit.friend.FriendHelper;
import net.astrocube.api.bukkit.translation.mode.AlertModes;
import net.astrocube.api.core.friend.FriendshipHandler;
import net.astrocube.commons.bukkit.utils.UserUtils;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;


public class AcceptSubCommand implements CommandClass {

	private @Inject MessageHandler messageHandler;
	private @Inject FriendHelper friendCommandValidator;
	private @Inject FriendshipHandler friendshipHandler;
	private @Inject FriendCallbackHelper friendCallbackHelper;

	@Command(names = "accept")
	public boolean execute(@Sender Player player, OfflinePlayer target) {

		if (UserUtils.checkSamePlayer(player, target, messageHandler)) {
			return true;
		}

		friendCallbackHelper.findUsers(player, target, (user, targetUser) -> {

			if (friendCommandValidator.checkAlreadyFriends(player, user, targetUser)) {
				messageHandler.sendIn(player, AlertModes.ERROR, "friends.error.already-friends");
				return;
			}

			if (!friendshipHandler.existsFriendRequest(targetUser.getId(), user.getId())) {
				messageHandler.sendIn(player, AlertModes.ERROR, "friends.error.no-friend-request");
				return;
			}

			friendshipHandler.deleteFriendRequest(targetUser.getId(), user.getId());
			friendshipHandler.createFriendship(user.getId(), targetUser.getId());

		});

		return true;

	}

}
