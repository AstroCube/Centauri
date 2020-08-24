package net.astrocube.lobby.hide.applier;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.astrocube.api.bukkit.friend.FriendHelper;
import net.astrocube.api.bukkit.lobby.hide.HideApplier;
import net.astrocube.api.core.service.find.FindService;
import net.astrocube.api.core.virtual.user.User;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

@Singleton
public class FriendHideApplier implements HideApplier {

    private @Inject FriendHelper friendHelper;
    private @Inject FindService<User> findService;

    @Override
    public void apply(User user, Player player) {
        Bukkit.getOnlinePlayers().forEach(onlinePlayer ->
                findService.find(onlinePlayer.getDatabaseIdentifier()).callback(playerRecord -> {
                    if (playerRecord.isSuccessful() && playerRecord.getResponse().isPresent()) {
                        if (friendHelper.checkAlreadyFriends(
                                onlinePlayer,
                                playerRecord.getResponse().get(),
                                user
                        )) {
                            player.showPlayer(onlinePlayer);
                        }
                    }
                })
        );
    }

}
