package net.astrocube.lobby.listener.gadget;

import com.google.inject.Inject;
import net.astrocube.api.bukkit.lobby.selector.lobby.LobbySelectorDisplay;
import net.astrocube.api.bukkit.user.inventory.event.ActionableItemEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;

public class LobbySelectorGadgetInteractListener implements Listener {

	private @Inject LobbySelectorDisplay lobbySelectorDisplay;

	@EventHandler
	public void onGameGadgetInteract(ActionableItemEvent event) {
		if (
			event.getAction().equalsIgnoreCase("lobby_selector") &&
				(event.getClick() == Action.RIGHT_CLICK_AIR || event.getClick() == Action.RIGHT_CLICK_BLOCK)
		) {
			lobbySelectorDisplay.openDisplay(event.getPlayer(), 1);
		}
	}
}
