package net.astrocube.lobby.listener.gadget;

import net.astrocube.api.bukkit.lobby.profile.UserProfileDisplay;
import net.astrocube.api.bukkit.user.inventory.event.ActionableItemEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;

import javax.inject.Inject;

public class ProfileGadgetInteractListener implements Listener {

	@Inject private UserProfileDisplay userProfileDisplay;

	@EventHandler
	public void onProfileGadgetInteract(ActionableItemEvent event) {
		if (
			event.getAction().equals("profile_menu")
				&& (event.getClick() == Action.RIGHT_CLICK_AIR || event.getClick() == Action.RIGHT_CLICK_BLOCK)
		) {
			userProfileDisplay.openDisplay(
				event.getUser(),
				event.getPlayer()
			);
		}
	}

}
