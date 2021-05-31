package net.astrocube.commons.bukkit.listener.game.session;

import com.google.inject.Inject;
import net.astrocube.api.bukkit.game.event.game.GameUserDisconnectEvent;
import net.astrocube.api.bukkit.game.exception.GameControlException;
import net.astrocube.api.bukkit.game.lobby.LobbySessionManager;
import net.astrocube.api.bukkit.game.match.UserMatchJoiner;
import net.astrocube.api.bukkit.virtual.game.match.Match;
import net.astrocube.api.core.service.find.FindService;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

import java.util.logging.Level;

public class LobbyUserDisconnectListener implements Listener {

	private @Inject LobbySessionManager lobbySessionManager;
	private @Inject FindService<Match> findService;
	private @Inject Plugin plugin;

	@EventHandler(priority = EventPriority.MONITOR)
	public void onGameUserJoin(GameUserDisconnectEvent event) {
		findService.find(event.getMatch()).callback(matchResponse -> {

			try {

				if (!matchResponse.isSuccessful() || !matchResponse.getResponse().isPresent()) {
					throw new GameControlException("Unsuccessful query at error update");
				}

				if (event.getOrigin() == UserMatchJoiner.Origin.WAITING) {
					lobbySessionManager.disconnectUser(event.getPlayer(), matchResponse.getResponse().get());
				}

			} catch (Exception e) {
				plugin.getLogger().log(Level.WARNING, "There was an error while updating the match assignation.", e);
			}
		});
	}

}
