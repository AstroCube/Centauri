package net.astrocube.commons.bukkit.command.friends;

import com.google.inject.Inject;
import me.fixeddev.ebcm.parametric.annotation.ACommand;
import me.fixeddev.ebcm.parametric.annotation.Injected;
import me.yushust.message.core.MessageProvider;
import net.astrocube.api.core.friend.FriendshipHandler;
import net.astrocube.api.core.service.find.FindService;
import net.astrocube.api.core.virtual.user.User;
import net.astrocube.commons.core.utils.Callbacks;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

@ACommand(names = "add")
public class AddSubCommand {

    private @Inject MessageProvider<Player> messageProvider;
    private @Inject FriendshipHandler friendshipHandler;
    private @Inject FindService<User> userFindService;
    private @Inject FriendCommandValidator friendCommandValidator;

    @ACommand(names = "")
    public boolean execute(@Injected(true) Player player, OfflinePlayer target) {

        if (friendCommandValidator.checkSamePlayer(player, target)) {
            return true;
        }


        userFindService.find(player.getDatabaseIdentifier()).callback(Callbacks.applyCommonErrorHandler(
"Find userdata of " + player.getName(),
            user -> {

                // TODO: Find the offlineplayer by name using UserFindService

            }
        ));

        return true;

    }

}
