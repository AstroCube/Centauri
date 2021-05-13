package net.astrocube.commons.bukkit.command.friends;

import com.google.inject.Inject;
import me.fixeddev.commandflow.annotated.CommandClass;
import me.fixeddev.commandflow.annotated.annotation.Command;
import me.fixeddev.commandflow.bukkit.annotation.Sender;
import me.yushust.message.MessageHandler;
import net.astrocube.api.bukkit.friend.FriendHelper;
import net.astrocube.api.bukkit.translation.mode.AlertModes;
import net.astrocube.api.bukkit.user.display.DisplayMatcher;
import net.astrocube.api.bukkit.user.display.TranslatedFlairFormat;
import net.astrocube.api.core.friend.FriendshipHandler;
import net.astrocube.commons.bukkit.utils.ChatAlertLibrary;
import net.astrocube.commons.bukkit.utils.UserUtils;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

@Command(names = "deny")
public class DenySubCommand implements CommandClass {

	private @Inject MessageHandler messageHandler;
	private @Inject FriendHelper friendCommandValidator;
	private @Inject DisplayMatcher displayMatcher;
	private @Inject FriendshipHandler friendshipHandler;
	private @Inject FriendCallbackHelper friendCallbackHelper;

	@Command(names = "")
	public boolean execute(@Sender Player player, OfflinePlayer target) {

		if (UserUtils.checkSamePlayer(player, target, messageHandler)) {
			return true;
		}

		friendCallbackHelper.findUsers(player, target, (user, targetUser) -> {

			if (friendCommandValidator.checkAlreadyFriends(player, user, targetUser)) {
				return;
			}

			if (!friendshipHandler.existsFriendRequest(targetUser.getId(), user.getId())) {
				messageHandler.get(player, "friend.error.already-friends");
				return;
			}

			friendshipHandler.removeFriendRequest(targetUser.getId(), user.getId());

			TranslatedFlairFormat matcher = displayMatcher.getDisplay(player, targetUser);

			messageHandler.sendReplacingIn(
				player, AlertModes.ERROR, "friend.request.denied",
				"%sender%", matcher.getColor() + targetUser.getDisplay()
			);

		});

		return true;

	}

}
