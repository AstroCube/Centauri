package net.astrocube.commons.bukkit.command.friends;

import com.google.inject.Inject;
import me.fixeddev.commandflow.annotated.CommandClass;
import me.fixeddev.commandflow.annotated.annotation.Command;
import me.fixeddev.commandflow.annotated.annotation.OptArg;
import me.fixeddev.commandflow.annotated.annotation.Switch;
import me.fixeddev.commandflow.bukkit.annotation.Sender;
import me.yushust.message.MessageHandler;
import net.astrocube.api.bukkit.friend.FriendHelper;
import net.astrocube.api.bukkit.translation.mode.AlertModes;
import net.astrocube.api.core.friend.FriendshipHandler;
import net.astrocube.api.core.service.create.CreateService;
import net.astrocube.api.core.service.find.FindService;
import net.astrocube.api.core.virtual.friend.Friendship;
import net.astrocube.api.core.virtual.friend.FriendshipDoc;
import net.astrocube.api.core.virtual.user.User;
import net.astrocube.commons.bukkit.utils.UserUtils;
import net.astrocube.commons.core.utils.Callbacks;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;

@Command(names = "force")
public class ForceSubCommand implements CommandClass {

    private @Inject MessageHandler messageHandler;
    private @Inject FriendshipHandler friendshipHandler;
    private @Inject FriendHelper friendHelper;
    private @Inject FindService<User> findService;
    private @Inject FriendCallbackHelper friendCallbackHelper;

    @Command(names = "")
    public boolean execute(@Sender Player player, @Switch("s") Boolean silent, OfflinePlayer target, @OptArg OfflinePlayer second) {

        if (UserUtils.checkSamePlayer(player, target, messageHandler)) {
            return true;
        }

        String issuer = player.getDatabaseIdentifier();

        if (second != null && second.getName().equalsIgnoreCase(player.getName())) {
            second = null;
        }

        OfflinePlayer finalSecond = second;
        OfflinePlayer finalSecond1 = second;
        findService.find(issuer).callback(issuerResponse -> {

            if (!issuerResponse.isSuccessful()) {
                messageHandler.sendIn(player, AlertModes.ERROR, "friend.error.internal");
            }

            issuerResponse.ifSuccessful(issuerRecord ->
                    friendCallbackHelper.findUserByName(target.getName(), (exception, user) -> {

                        if (!user.isPresent()) {
                            messageHandler.sendIn(player, AlertModes.ERROR, "commands.unknown");
                            return;
                        }

                        if (finalSecond == null) {

                            if (friendHelper.checkAlreadySent(player, issuerRecord, user.get())) {
                                return;
                            }

                            if (friendHelper.checkAlreadyFriends(player, issuerRecord, user.get())) {
                                return;
                            }

                            friendshipHandler.forceFriendship(issuer, issuer, user.get().getId(), false);
                        }

                        friendCallbackHelper.findUserByName(finalSecond1.getName(), (secondException, secondUser) -> {

                            if (!secondUser.isPresent()) {
                                messageHandler.sendIn(player, AlertModes.ERROR, "commands.unknown");
                                return;
                            }

                            if (friendHelper.checkAlreadySent(player, secondUser.get(), user.get())) {
                                return;
                            }

                            if (friendHelper.checkAlreadyFriends(player, secondUser.get(), user.get())) {
                                return;
                            }

                            friendshipHandler.forceFriendship(issuer, secondUser.get().getId(), user.get().getId(), !silent);

                        });


                    })
            );

        });

        return true;

    }

}
