package net.astrocube.commons.bukkit.command.friends;

import com.google.inject.Inject;
import me.fixeddev.commandflow.annotated.CommandClass;
import me.fixeddev.commandflow.annotated.annotation.Command;
import me.fixeddev.commandflow.bukkit.annotation.Sender;
import me.yushust.message.MessageHandler;
import net.astrocube.api.bukkit.friend.FriendHelper;
import net.astrocube.api.core.service.create.CreateService;
import net.astrocube.api.core.virtual.friend.Friendship;
import net.astrocube.api.core.virtual.friend.FriendshipDoc;
import net.astrocube.commons.bukkit.utils.UserUtils;
import net.astrocube.commons.core.utils.Callbacks;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;

@Command(names = "force")
public class ForceSubCommand implements CommandClass {

    private @Inject MessageHandler<Player> messageHandler;
    private @Inject CreateService<Friendship, FriendshipDoc.Partial> createService;
    private @Inject FriendHelper friendHelper;
    private @Inject FriendCallbackHelper friendCallbackHelper;

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

            createService.create(new FriendshipDoc.Creation() {
                @Nullable
                @Override
                public String getIssuer() {
                    return user.getId();
                }
                @Override
                public boolean isAlerted() {
                    return false;
                }
                @Override
                public String getSender() {
                    return user.getId();
                }
                @Override
                public String getReceiver() {
                    return targetUser.getId();
                }
            }).callback(Callbacks.applyCommonErrorHandler(friendship ->
                    player.sendMessage(
                            messageHandler.get(player, "commons-friend-forced")
                                    .replace("%%receiver%%", target.getName())
                    )
            ));

        });

        return true;

    }

}
