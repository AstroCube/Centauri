package net.astrocube.commons.bukkit.command.friends;

import com.google.inject.Inject;
import me.fixeddev.ebcm.bukkit.parameter.provider.annotation.Sender;
import me.fixeddev.ebcm.parametric.CommandClass;
import me.fixeddev.ebcm.parametric.annotation.ACommand;
import me.fixeddev.ebcm.parametric.annotation.Injected;
import me.yushust.message.core.MessageProvider;
import net.astrocube.api.bukkit.friend.FriendHelper;
import net.astrocube.api.core.service.create.CreateService;
import net.astrocube.api.core.virtual.friend.Friendship;
import net.astrocube.api.core.virtual.friend.FriendshipDoc;
import net.astrocube.commons.bukkit.utils.UserUtils;
import net.astrocube.commons.core.utils.Callbacks;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;

@ACommand(names = "force")
public class ForceSubCommand implements CommandClass {

    private @Inject MessageProvider<Player> messageProvider;
    private @Inject CreateService<Friendship, FriendshipDoc.Partial> createService;
    private @Inject
    FriendHelper friendHelper;
    private @Inject FriendCallbackHelper friendCallbackHelper;

    @ACommand(names = "")
    public boolean execute(@Injected(true) @Sender Player player, OfflinePlayer target) {

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
                            messageProvider.getMessage(player, "commons-friend-forced")
                                    .replace("%%receiver%%", target.getName())
                    )
            ));

        });

        return true;

    }

}
