package net.astrocube.lobby.hide.applier;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.astrocube.api.bukkit.friend.FriendHelper;
import net.astrocube.api.bukkit.lobby.hide.HideApplier;
import net.astrocube.api.core.virtual.user.User;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

@Singleton
public class FriendHideApplier implements HideApplier {

    private @Inject FriendHelper friendHelper;
    private @Inject Plugin plugin;

    @Override
    public void apply(User user, Player player, User target, Player targetPlayer) {
        if (friendHelper.checkAlreadyFriends(targetPlayer, target, user)) {
            Bukkit.getScheduler().runTask(plugin, () -> player.showPlayer(targetPlayer));
        }
    }
}
