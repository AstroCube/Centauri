package net.astrocube.commons.bukkit.listener.lobby;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class LobbyCropBreakingListener implements Listener {

	@EventHandler
	public void onCropBreak(PlayerInteractEvent event) {
		if (event.getAction() == Action.PHYSICAL &&
			event.getClickedBlock().getType() == Material.SOIL
		) {
			event.setCancelled(true);
		}
	}
}