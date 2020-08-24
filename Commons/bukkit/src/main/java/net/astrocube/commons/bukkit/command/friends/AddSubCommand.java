package net.astrocube.commons.bukkit.command.friends;

import com.google.inject.Inject;
import me.fixeddev.ebcm.parametric.CommandClass;
import me.fixeddev.ebcm.parametric.annotation.ACommand;
import me.fixeddev.ebcm.parametric.annotation.Injected;
import me.yushust.message.core.MessageProvider;
import net.astrocube.api.bukkit.friend.FriendHelper;
import net.astrocube.api.core.friend.FriendshipHandler;
import net.astrocube.commons.bukkit.utils.UserUtils;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

@ACommand(names = "add")
public class AddSubCommand implements CommandClass {

    private @Inject MessageProvider<Player> messageProvider;
    private @Inject
    FriendHelper friendHelper;
    private @Inject FriendshipHandler friendshipHandler;
    private @Inject FriendCallbackHelper friendCallbackHelper;

    @ACommand(names = "")
    public boolean execute(@Injected(true) Player player, OfflinePlayer target) {

        if (UserUtils.checkSamePlayer(player, target, messageProvider)) {
            return true;
        }

        friendCallbackHelper.findUsers(player, target, (user, targetUser) -> {

            if (friendHelper.checkAlreadySent(player, user, targetUser)) {
                return;
            }

            if (friendHelper.checkAlreadyFriends(player, user, targetUser)) {
                return;
            }

            friendshipHandler.createFriendRequest(user.getId(), targetUser.getId());
            player.sendMessage(
                    messageProvider.getMessage(player, "commons-friend-request-sent")
                            .replace("%%receiver%%", target.getName())
            );

        });

        return true;

    }

}
