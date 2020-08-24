package net.astrocube.lobby.hide;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.astrocube.api.bukkit.lobby.hide.HideCompound;
import net.astrocube.api.bukkit.lobby.hide.HideStatusModifier;
import net.astrocube.api.bukkit.lobby.hide.HideApplier;
import net.astrocube.api.core.virtual.user.User;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import javax.inject.Named;

@Singleton
public class CoreHideStatusModifier implements HideStatusModifier {

    private @Inject @Named("friend") HideApplier friendHide;
    private @Inject @Named("permission") HideApplier permissionHide;
    private @Inject @Named("staff") HideApplier staffHide;

    @Override
    public void apply(User user, HideCompound compound) {

        Player player = Bukkit.getPlayer(user.getUsername());

        if (player != null) {

            Bukkit.getOnlinePlayers().forEach(player::hidePlayer);
            if (compound.friends()) staffHide.apply(user, player);
            if (compound.permission()) permissionHide.apply(user, player);
            if (compound.staff()) friendHide.apply(user, player);

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
