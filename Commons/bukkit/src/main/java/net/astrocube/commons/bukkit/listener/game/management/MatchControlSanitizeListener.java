package net.astrocube.commons.bukkit.listener.game.management;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.inject.Inject;
import net.astrocube.api.bukkit.game.event.match.MatchControlSanitizeEvent;
import net.astrocube.api.bukkit.game.match.control.MatchScheduler;
import net.astrocube.api.bukkit.game.match.control.PendingMatchFinder;
import net.astrocube.api.bukkit.game.matchmaking.error.MatchmakingErrorBroadcaster;
import net.astrocube.api.bukkit.game.scheduler.RunningMatchBalancer;
import net.astrocube.api.bukkit.virtual.game.match.Match;
import net.astrocube.api.bukkit.virtual.game.match.MatchDoc;
import net.astrocube.api.core.server.ServerService;
import net.astrocube.api.core.service.query.QueryService;
import net.astrocube.api.core.virtual.server.Server;
import net.astrocube.commons.bukkit.game.matchmaking.CoreAvailableMatchProvider;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

import java.util.logging.Level;


public class MatchControlSanitizeListener implements Listener {

	private @Inject MatchScheduler matchmakingScheduler;
	private @Inject PendingMatchFinder pendingMatchFinder;
	private @Inject ServerService serverService;
	private @Inject ObjectMapper mapper;
	private @Inject QueryService<Match> queryService;
	private @Inject MatchmakingErrorBroadcaster matchmakingErrorBroadcaster;
	private @Inject RunningMatchBalancer runningMatchBalancer;
	private @Inject Plugin plugin;

	@EventHandler
	public void onMatchControlSanitize(MatchControlSanitizeEvent event) {

		try {

			if (plugin.getConfig().getBoolean("server.sandbox")) {
				plugin.getLogger().log(Level.INFO, "Skipping sanitization due to sandbox mode. Starting new temp match.");
				matchmakingScheduler.schedule();
				return;
			}

			Server server = serverService.getActual();

			pendingMatchFinder.getPendingMatches(event.getGameMode(), event.getSubGameMode())
				.forEach(pending -> {

					if (runningMatchBalancer.hasCapacity()) {
						try {
							matchmakingScheduler.schedule(pending);
							// TODO: Invalidate if created
						} catch (Exception e) {
							plugin.getLogger().log(Level.WARNING, "Can not assign a match", e);
							try {
								matchmakingErrorBroadcaster.broadcastError(pending, e.getMessage());
							} catch (JsonProcessingException ex) {
								plugin.getLogger().log(Level.SEVERE, "Error serializing error for broadcasting", e);
							}
						}
					}

				});

			ObjectNode node = mapper.createObjectNode();
			node.put("server", server.getId());
			node.put("gamemode", event.getGameMode().getId());
			node.put("subGamemode", event.getSubGameMode().getId());

			queryService.query(node).callback(callbacks -> {

				if (!callbacks.isSuccessful() || !callbacks.getResponse().isPresent()) {
					plugin.getLogger().warning("Can not obtain sanitize matches.");
				}

				if (callbacks.getResponse()
					.get()
					.getFoundModels()
					.stream()
					.noneMatch(match -> match.getStatus() == MatchDoc.Status.LOBBY && CoreAvailableMatchProvider.getRemainingSpace(match) > 0)) {
					try {
						if (runningMatchBalancer.hasCapacity()) {
							matchmakingScheduler.schedule();
						}
					} catch (Exception e) {
						plugin.getLogger().log(Level.WARNING, "There was an error trying to create match at sanitizing.", e);
					}
				}

			});


		} catch (Exception e) {
			plugin.getLogger().log(Level.SEVERE, "There was an error trying to sanitize matches.", e);
		}

	}


}
