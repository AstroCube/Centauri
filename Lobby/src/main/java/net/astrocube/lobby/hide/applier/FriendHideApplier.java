package net.astrocube.lobby.hide.applier;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.astrocube.api.bukkit.friend.FriendHelper;
import net.astrocube.api.bukkit.lobby.hide.HideApplier;
import net.astrocube.api.core.virtual.user.User;
import org.bukkit.entity.Player;

@Singleton
public class FriendHideApplier implements HideApplier {

    private @Inject FriendHelper friendHelper;

    @Override
    public void apply(User user, Player player, User target, Player targetPlayer) {
        if (friendHelper.checkAlreadyFriends(targetPlayer, target, user)) {
            player.showPlayer(targetPlayer);
        }
    }
}
