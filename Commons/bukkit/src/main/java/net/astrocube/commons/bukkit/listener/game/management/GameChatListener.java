package net.astrocube.commons.bukkit.listener.game.management;

import com.google.inject.Inject;
import net.astrocube.api.bukkit.game.channel.MatchMessageBroadcaster;
import net.astrocube.api.core.server.ServerService;
import net.astrocube.api.core.virtual.server.ServerDoc;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.Plugin;

import java.util.logging.Level;

public class GameChatListener implements Listener {

	private @Inject MatchMessageBroadcaster matchMessageBroadcaster;
	private @Inject Plugin plugin;
	private @Inject ServerService serverService;

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerAsyncChat(AsyncPlayerChatEvent event) {

		if (!event.isCancelled()) {
			try {
				if (serverService.getActual().getServerType().equals(ServerDoc.Type.GAME)) {
					matchMessageBroadcaster.sendMessage(event.getMessage(), event.getPlayer());
					event.setCancelled(true);
				}
			} catch (Exception e) {
				plugin.getLogger().log(Level.SEVERE, "Error while obtaining actual server mode. Cancelling chat.", e);
			}
		}

	}

}
