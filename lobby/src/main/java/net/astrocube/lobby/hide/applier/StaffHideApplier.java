package net.astrocube.lobby.hide.applier;

import com.google.inject.Singleton;

import net.astrocube.api.bukkit.lobby.hide.HideApplier;
import net.astrocube.api.core.virtual.user.User;

import org.bukkit.entity.Player;

@Singleton
public class StaffHideApplier implements HideApplier {

	@Override
	public void apply(User user, Player player, User target, Player targetPlayer) {
		if (target.getGroups().stream().anyMatch(userGroup -> userGroup.getGroup().isStaff())) {
			player.showPlayer(targetPlayer);
		} else {
			player.hidePlayer(targetPlayer);
		}
	}
}