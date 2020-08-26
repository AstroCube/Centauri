package net.astrocube.commons.bukkit.command.friends;

import com.google.inject.Inject;
import me.fixeddev.ebcm.bukkit.parameter.provider.annotation.Sender;
import me.fixeddev.ebcm.parametric.CommandClass;
import me.fixeddev.ebcm.parametric.annotation.ACommand;
import me.fixeddev.ebcm.parametric.annotation.Injected;
import me.yushust.message.core.MessageProvider;
import net.astrocube.api.core.friend.FriendshipHandler;
import net.astrocube.api.core.service.create.CreateService;
import net.astrocube.api.core.virtual.friend.Friendship;
import net.astrocube.api.core.virtual.friend.FriendshipDoc;
import net.astrocube.commons.bukkit.utils.ChatAlertLibrary;
import net.astrocube.commons.core.utils.Callbacks;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;

@ACommand(names = "add")
public class DenySubCommand implements CommandClass {

    private @Inject MessageProvider<Player> messageProvider;
    private @Inject FriendCommandValidator friendCommandValidator;
    private @Inject FriendshipHandler friendshipHandler;
    private @Inject FriendCallbackHelper friendCallbackHelper;
    private @Inject CreateService<Friendship, FriendshipDoc.Partial> createService;

    @ACommand(names = "")
    public boolean execute(@Injected(true) @Sender Player player, OfflinePlayer target) {

        if (friendCommandValidator.checkSamePlayer(player, target)) {
            return true;
        }

        friendCallbackHelper.findUsers(player, target, (user, targetUser) -> {

            if (friendCommandValidator.checkAlreadyFriends(player, user, targetUser)) {
                return;
            }

            if (!friendshipHandler.existsFriendRequest(player.getDatabaseIdentifier(), targetUser.getId())) {
                ChatAlertLibrary.alertChatError(
                        player,
                        messageProvider.getMessage(player, "commons-friend-no-friend-request")
                );
                return;
            }

            //TODO: Remove friend request

        });

        return true;

    }

}
