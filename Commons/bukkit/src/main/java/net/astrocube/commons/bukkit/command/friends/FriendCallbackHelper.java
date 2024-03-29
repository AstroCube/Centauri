package net.astrocube.commons.bukkit.command.friends;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.astrocube.api.core.service.find.FindService;
import net.astrocube.api.core.service.query.QueryService;
import net.astrocube.api.core.virtual.user.User;
import net.astrocube.commons.core.utils.Callbacks;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.function.BiConsumer;

@Singleton
public class FriendCallbackHelper {

    private @Inject FindService<User> userFindService;
    private @Inject QueryService<User> userQueryService;
    private @Inject ObjectMapper objectMapper;

    public void findUsers(Player player, OfflinePlayer target, BiConsumer<User, User> callback) {

        userFindService.find(player.getDatabaseIdentifier())
                .callback(Callbacks.applyCommonErrorHandler("Find userdata of " + player.getName(),
                        user -> {

                            ObjectNode filter = objectMapper.createObjectNode()
                                    .put("username", target.getName());

                            userQueryService.query(filter).callback(Callbacks.applyCommonErrorHandler("Find userdata of " + target.getName(), result -> {

                                User targetUser = result.getFoundModels().iterator().next();
                                callback.accept(user, targetUser);

                            }));

                        }
                ));

    }

}
