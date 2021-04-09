package net.astrocube.commons.bukkit.command.friends;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.inject.Inject;
import me.fixeddev.commandflow.annotated.CommandClass;
import me.fixeddev.commandflow.annotated.annotation.Command;
import me.fixeddev.commandflow.bukkit.annotation.Sender;
import me.yushust.message.MessageHandler;
import net.astrocube.api.bukkit.friend.FriendHelper;
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
                                .put("issuer", user.getId())
                    );

            queryService.query(node).callback(Callbacks.applyCommonErrorHandler(result ->
                deleteService.delete(
                        result.getFoundModels()
                            .iterator()
                            .next()
                            .getId()
                )
            ));

            player.sendMessage(
                    messageHandler.get(player, "commons-friend-removed")
                        .replace("%receiver%", targetUser.getId())
            );

        });

        return true;
    }

}
