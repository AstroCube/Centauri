package net.astrocube.lobby.hide;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.astrocube.api.bukkit.lobby.hide.HideApplier;
import net.astrocube.api.bukkit.lobby.hide.HideCompound;
import net.astrocube.api.bukkit.lobby.hide.HideCompoundMatcher;
import net.astrocube.api.bukkit.lobby.hide.HideStatusModifier;
import net.astrocube.api.core.service.find.FindService;
import net.astrocube.api.core.virtual.user.User;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import javax.inject.Named;

@Singleton
public class CoreHideStatusModifier implements HideStatusModifier {

    private @Inject @Named("friend") HideApplier friendHide;
    private @Inject @Named("permission") HideApplier permissionHide;
    private @Inject @Named("staff") HideApplier staffHide;
    private @Inject HideCompoundMatcher hideCompoundMatcher;
    private @Inject Plugin plugin;
    private @Inject FindService<User> findService;

    @Override
    public void globalApply(User user) {

        Player player = Bukkit.getPlayer(user.getUsername());

        if (player != null) {
            Bukkit.getOnlinePlayers().forEach(online -> {
                Bukkit.getScheduler().runTask(plugin, () -> player.hidePlayer(online));
                if (!online.getDatabaseIdentifier().equals(user.getId())) {
                    findService.find(online.getDatabaseIdentifier()).callback(userResponse -> {
                        if (userResponse.isSuccessful() && userResponse.getResponse().isPresent())  {
                            individualApply(
                                    user,
                                    userResponse.getResponse().get(),
                                    hideCompoundMatcher.getUserCompound(user)
                            );
                        }
                    });
                }
            });
        }
    }

    @Override
    public void individualApply(User user, User target, HideCompound compound) {

        Player player = Bukkit.getPlayer(user.getUsername());
        Player targetPlayer = Bukkit.getPlayer(target.getUsername());

        if (player != null && targetPlayer != null) {
            Bukkit.getScheduler().runTask(plugin, () -> {
                player.hidePlayer(targetPlayer);
                if (compound.friends()) staffHide.apply(user, player, target, targetPlayer);
                if (compound.permission()) permissionHide.apply(user, player, target, targetPlayer);
                if (compound.staff()) friendHide.apply(user, player, target, targetPlayer);
            });
        }

    }

    @Override
    public void restore(User user) {
        Player player = Bukkit.getPlayer(user.getUsername());
        if (player != null) {
            Bukkit.getOnlinePlayers().forEach(player::showPlayer);
        }
    }

}
