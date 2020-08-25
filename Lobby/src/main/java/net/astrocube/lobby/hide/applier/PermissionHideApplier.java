package net.astrocube.lobby.hide.applier;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.astrocube.api.bukkit.lobby.hide.HideApplier;
import net.astrocube.api.core.virtual.user.User;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

@Singleton
public class PermissionHideApplier implements HideApplier {


    private @Inject
    Plugin plugin;

    @Override
    public void apply(User user, Player player, User target, Player targetPlayer) {
        if (targetPlayer.hasPermission("lobby.hide.bypass")) {
            Bukkit.getScheduler().runTask(plugin, () -> player.showPlayer(targetPlayer));
        }
    }

}
