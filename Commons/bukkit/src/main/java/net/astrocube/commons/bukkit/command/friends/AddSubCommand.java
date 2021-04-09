package net.astrocube.commons.bukkit.command.friends;

import com.google.inject.Inject;
import me.fixeddev.commandflow.annotated.CommandClass;
import me.fixeddev.commandflow.annotated.annotation.Command;
import me.fixeddev.commandflow.bukkit.annotation.Sender;
import me.yushust.message.MessageHandler;
import net.astrocube.api.bukkit.friend.FriendHelper;
import net.astrocube.api.bukkit.user.display.DisplayMatcher;
import net.astrocube.api.bukkit.user.display.TranslatedFlairFormat;
import net.astrocube.api.bukkit.user.display.TranslatedGroupProvider;
import net.astrocube.api.core.friend.FriendshipHandler;
import net.astrocube.commons.bukkit.utils.UserUtils;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

@Command(names = "add")
public class AddSubCommand implements CommandClass {

    private @Inject MessageHandler messageHandler;
    private @Inject FriendHelper friendHelper;
    private @Inject FriendshipHandler friendshipHandler;
    private @Inject FriendCallbackHelper friendCallbackHelper;}
    private @Inject DisplayMatcher displayMatcher;

    @Command(names = "")
    public boolean execute(@Sender Player player, OfflinePlayer target) {

        if (UserUtils.checkSamePlayer(player, target, messageHandler)) {
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

            TranslatedFlairFormat flairFormat = displayMatcher.getDisplay(player, targetUser);

            messageHandler.sendReplacing(
                    player, "friend.request-sent",
                    "%receiver%", flairFormat.getColor() + targetUser.getDisplay()
            );

        });

        return true;

    }

}
