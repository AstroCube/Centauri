package net.astrocube.commons.bukkit.game.match;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import me.yushust.message.MessageHandler;
import net.astrocube.api.bukkit.game.match.MatchMapUpdater;
import net.astrocube.api.bukkit.translation.mode.AlertModes;
import net.astrocube.api.bukkit.virtual.game.match.Match;
import net.astrocube.api.bukkit.virtual.game.match.MatchDoc;
import net.astrocube.api.core.service.find.FindService;
import net.astrocube.api.core.service.update.UpdateService;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

@Singleton
public class CoreMatchMapUpdater implements MatchMapUpdater {

	private @Inject MessageHandler messageHandler;
	private @Inject UpdateService<Match, MatchDoc.Partial> updateService;
	private @Inject FindService<Match> findService;
	private @Inject Plugin plugin;

	@Override
	public void updateMatch(String match, String map, String requester) {
		findService.find(match).callback(matchCallback -> {

			if (!matchCallback.isSuccessful()) {
				alertMessage(requester, AlertModes.ERROR, "game.admin.lobby.map.error");
			}

			matchCallback.ifSuccessful(matchResponse -> {
				matchResponse.setMap(map);
				updateService.update(matchResponse).callback(updateCallback -> {

					if (!updateCallback.isSuccessful()) {
						alertMessage(requester, AlertModes.ERROR, "game.admin.lobby.map.error");
					}

					alertMessage(requester, AlertModes.INFO, "game.admin.lobby.map.success");
				});
			});
		});
	}

	private void alertMessage(String id, String mode, String translation) {

		Player player = Bukkit.getPlayerByIdentifier(id);

		if (player != null) {
			Bukkit.getScheduler().runTask(
				plugin,
				() -> {
					messageHandler.sendIn(player, mode, translation);
					player.closeInventory();
				});
		}
	}

}
