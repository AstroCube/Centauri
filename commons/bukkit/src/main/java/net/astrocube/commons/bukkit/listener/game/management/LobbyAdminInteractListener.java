package net.astrocube.commons.bukkit.listener.game.management;

import com.google.inject.Inject;
import me.yushust.message.MessageHandler;
import net.astrocube.api.bukkit.game.match.control.menu.MatchLobbyMenuProvider;
import net.astrocube.api.bukkit.translation.mode.AlertModes;
import net.astrocube.api.bukkit.user.inventory.event.ActionableItemEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.plugin.Plugin;

import java.util.logging.Level;

public class LobbyAdminInteractListener implements Listener {

	private @Inject MatchLobbyMenuProvider matchLobbyMenuProvider;
	private @Inject MessageHandler messageHandler;
	private @Inject Plugin plugin;

	@EventHandler
	public void onAdminGadgetInteract(ActionableItemEvent event) {
		if (
			event.getAction().equalsIgnoreCase("gc_admin_lobby") &&
				(event.getClick() == Action.RIGHT_CLICK_AIR || event.getClick() == Action.RIGHT_CLICK_BLOCK)
		) {
			try {
				matchLobbyMenuProvider.create(event.getPlayer());
			} catch (Exception e) {
				messageHandler.sendIn(event.getPlayer(), AlertModes.ERROR, "game.admin.lobby.error");
				plugin.getLogger().log(Level.SEVERE, "Error while opening menu gadget event", e);
			}
		}
	}
}
