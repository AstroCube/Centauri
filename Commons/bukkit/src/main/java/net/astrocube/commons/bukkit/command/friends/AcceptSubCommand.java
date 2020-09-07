package net.astrocube.commons.bukkit.command.friends;

import com.google.inject.Inject;
import me.fixeddev.ebcm.bukkit.parameter.provider.annotation.Sender;
import me.fixeddev.ebcm.parametric.CommandClass;
import me.fixeddev.ebcm.parametric.annotation.ACommand;
import me.fixeddev.ebcm.parametric.annotation.Injected;
import me.yushust.message.MessageHandler;
import net.astrocube.api.bukkit.friend.FriendHelper;
import net.astrocube.api.core.friend.FriendshipHandler;
import net.astrocube.commons.bukkit.utils.ChatAlertLibrary;
import net.astrocube.commons.bukkit.utils.UserUtils;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

@ACommand(names = "accept")
public class AcceptSubCommand implements CommandClass {

    private @Inject MessageHandler<Player> messageHandler;
    private @Inject FriendHelper friendCommandValidator;
    private @Inject FriendshipHandler friendshipHandler;
    private @Inject FriendCallbackHelper friendCallbackHelper;

    @ACommand(names = "")
    public boolean execute(@Injected(true) @Sender Player player, OfflinePlayer target) {

        if (UserUtils.checkSamePlayer(player, target, messageHandler)) {
            return true;
        }

        friendCallbackHelper.findUsers(player, target, (user, targetUser) -> {

            if (friendCommandValidator.checkAlreadyFriends(player, user, targetUser)) {
                return;
            }

            if (!friendshipHandler.existsFriendRequest(targetUser.getId(), user.getId())) {
                ChatAlertLibrary.alertChatError(
                        player,
                        messageHandler.getMessage(player, "commons-friend-no-friend-request")
                );
                return;
            }

            friendshipHandler.createFriendship(user.getId(), targetUser.getId(), friendship ->
                messageHandler.sendMessage(player, "commons-friend-request-accepted")
            );

            friendshipHandler.removeFriendRequest(targetUser.getId(), user.getId());

        });

        return true;

    }

}
