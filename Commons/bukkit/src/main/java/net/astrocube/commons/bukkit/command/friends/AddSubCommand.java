package net.astrocube.commons.bukkit.command.friends;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.inject.Inject;
import me.fixeddev.ebcm.parametric.CommandClass;
import me.fixeddev.ebcm.parametric.annotation.ACommand;
import me.fixeddev.ebcm.parametric.annotation.Injected;
import me.yushust.message.core.MessageProvider;
import net.astrocube.api.core.friend.FriendshipHandler;
import net.astrocube.api.core.service.find.FindService;
import net.astrocube.api.core.service.query.QueryService;
import net.astrocube.api.core.virtual.user.User;
import net.astrocube.commons.core.utils.Callbacks;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

@ACommand(names = "add")
public class AddSubCommand implements CommandClass {

    private @Inject MessageProvider<Player> messageProvider;
    private @Inject FindService<User> userFindService;
    private @Inject FriendCommandValidator friendCommandValidator;
    private @Inject QueryService<User> userQueryService;
    private @Inject ObjectMapper objectMapper;
    private @Inject FriendshipHandler friendshipHandler;

    @ACommand(names = "")
    public boolean execute(@Injected(true) Player player, OfflinePlayer target) {

        if (friendCommandValidator.checkSamePlayer(player, target)) {
            return true;
        }

        userFindService.find(player.getDatabaseIdentifier())
            .callback(Callbacks.applyCommonErrorHandler("Find userdata of " + player.getName(),
                user -> {

                    ObjectNode filter = objectMapper.createObjectNode()
                            .put("username", target.getName());

                    userQueryService.query(filter).callback(Callbacks.applyCommonErrorHandler("Find userdata of " + target.getName(), result -> {

                        User targetUser = result.getFoundModels().iterator().next();

                        if (friendCommandValidator.checkAlreadySent(player, user, targetUser)) {
                            return;
                        }

                        if (friendCommandValidator.checkAlreadyFriends(player, user, targetUser)) {
                            return;
                        }

                        friendshipHandler.createFriendRequest(user.getId(), targetUser.getId());
                        player.sendMessage(
                                messageProvider.getMessage(player, "commons-friend-request-sent")
                                    .replace("%%receiver%%", target.getName())
                        );

                    }));

                }
            ));

        return true;

    }

}
