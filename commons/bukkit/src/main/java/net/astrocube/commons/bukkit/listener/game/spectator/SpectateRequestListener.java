package net.astrocube.commons.bukkit.listener.game.spectator;

import com.google.inject.Inject;
import me.yushust.message.MessageHandler;
import net.astrocube.api.bukkit.game.event.spectator.SpectateRequestEvent;
import net.astrocube.api.bukkit.game.match.MatchAssigner;
import net.astrocube.api.bukkit.game.match.MatchService;
import net.astrocube.api.bukkit.game.spectator.SpectateRequest;
import net.astrocube.api.bukkit.translation.mode.AlertModes;
import net.astrocube.api.bukkit.virtual.game.match.Match;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

import java.util.logging.Level;

public class SpectateRequestListener implements Listener {

	private @Inject MessageHandler messageHandler;
	private @Inject MatchService matchService;
	private @Inject MatchAssigner matchAssigner;
	private @Inject Plugin plugin;

	@EventHandler
	public void onSpectateRequest(SpectateRequestEvent event) {

		if (event.getState() == SpectateRequest.State.VOIDED) {
			messageHandler.sendIn(event.getPlayer(), AlertModes.ERROR, "game.spectator.request.voided");
			return;
		}

		if (event.getState() == SpectateRequest.State.ERROR) {
			messageHandler.sendIn(event.getPlayer(), AlertModes.ERROR, "game.spectator.request.error");
			return;
		}

		if (event.getState() == SpectateRequest.State.SUCCESS && event.getSpectateRequest() != null) {

			SpectateRequest request = event.getSpectateRequest();
			Match match = request.getMatch();

			try {
				matchService.assignSpectator(match, request.getRequester(), true);
				matchAssigner.setRecord(request.getRequester(), match.getId(), request.getServer());
				messageHandler.send(event.getPlayer(), "game.spectator.request.success");
			} catch (Exception e) {
				plugin.getLogger().log(Level.SEVERE, "There was an error assign spectators.", e);
			}

		}

	}


}
