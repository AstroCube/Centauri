package net.astrocube.lobby.hide.applier;

import com.google.inject.Singleton;
import net.astrocube.api.bukkit.lobby.hide.HideApplier;
import net.astrocube.api.core.virtual.user.User;
import org.bukkit.entity.Player;

@Singleton
public class PermissionHideApplier implements HideApplier {

    @Override
    public void apply(User user, Player player, User target, Player targetPlayer) {
        if (targetPlayer.hasPermission("lobby.hide.bypass")) {
            player.showPlayer(targetPlayer);
        }
    }

}
