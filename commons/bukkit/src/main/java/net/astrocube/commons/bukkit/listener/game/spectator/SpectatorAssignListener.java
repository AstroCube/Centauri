package net.astrocube.commons.bukkit.listener.game.spectator;

import com.google.inject.Inject;
import me.yushust.message.MessageHandler;
import net.astrocube.api.bukkit.game.event.game.GameUserJoinEvent;
import net.astrocube.api.bukkit.game.event.spectator.SpectatorAssignEvent;
import net.astrocube.api.bukkit.game.exception.GameControlException;
import net.astrocube.api.bukkit.game.match.MatchService;
import net.astrocube.api.bukkit.game.match.UserMatchJoiner;
import net.astrocube.api.bukkit.game.match.control.MatchParticipantsProvider;
import net.astrocube.api.bukkit.virtual.game.match.Match;
import net.astrocube.api.bukkit.virtual.game.match.MatchDoc;
import net.astrocube.api.core.service.find.FindService;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

import java.util.logging.Level;

public class SpectatorAssignListener implements Listener {

	private @Inject FindService<Match> findService;
	private @Inject MessageHandler messageHandler;
	private @Inject MatchService matchService;
	private @Inject Plugin plugin;

	@EventHandler
	public void onSpectatorAssign(SpectatorAssignEvent event) {
		findService.find(event.getMatch()).callback(response -> {
			try {

				if (!response.isSuccessful() || !response.getResponse().isPresent()) {
					throw new GameControlException("Can not retrieve from backend the match");
				}

				Player player = event.getPlayer();
				Match match = response.getResponse().get();

				matchService.assignSpectator(match, event.getPlayer().getDatabaseId(), true);

				if (MatchParticipantsProvider.getOnlinePlayers(match, MatchDoc.TeamMember::isActive).contains(player)) {
					matchService.disqualify(match.getId(), player.getDatabaseId());
				}

				Bukkit.getPluginManager().callEvent(
					new GameUserJoinEvent(
						event.getMatch(),
						player,
						UserMatchJoiner.Origin.SPECTATING
					)
				);

			} catch (Exception e) {
				plugin.getLogger().log(Level.SEVERE, "Can not invalidate match.", e);
				kickPlayer(event.getPlayer());
			}
		});
	}

	private void kickPlayer(Player player) {
		player.kickPlayer(ChatColor.RED + messageHandler.get(player, "game.lobby-error"));
	}

}
