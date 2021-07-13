package net.astrocube.commons.bukkit.listener.game.session;

import com.google.inject.Inject;
import me.yushust.message.MessageHandler;
import net.astrocube.api.bukkit.game.event.game.GameUserJoinEvent;
import net.astrocube.api.bukkit.game.exception.GameControlException;
import net.astrocube.api.bukkit.game.lobby.LobbySessionManager;
import net.astrocube.api.bukkit.game.spectator.SpectatorSessionManager;
import net.astrocube.api.bukkit.virtual.game.match.Match;
import net.astrocube.api.core.service.find.FindService;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

import java.util.logging.Level;

public class GameServerJoinListener implements Listener {

	private @Inject FindService<Match> findService;
	private @Inject LobbySessionManager lobbySessionManager;
	private @Inject SpectatorSessionManager spectatorSessionManager;
	private @Inject MessageHandler messageHandler;
	private @Inject Plugin plugin;

	@EventHandler
	public void onGameUserJoin(GameUserJoinEvent event) {

		findService.find(event.getMatch()).callback(matchResponse -> Bukkit.getScheduler().runTask(plugin, () -> {

			Player player = event.getPlayer();

			try {
				if (!matchResponse.isSuccessful() || !matchResponse.getResponse().isPresent()) {
					throw new GameControlException("Match not found");
				}

				player.getInventory().clear();
				player.getInventory().setArmorContents(null);

				Match match = matchResponse.getResponse().get();

				switch (event.getOrigin()) {
					case WAITING: {
						lobbySessionManager.connectUser(player, match);
						break;
					}
					case SPECTATING: {
						spectatorSessionManager.provideFunctions(player, match);
						break;
					}
					default: {
						kickPlayer(player);
						break;
					}
				}

			} catch (Exception exception) {
				plugin.getLogger().log(Level.WARNING, "There was an error while updating the match assignation.", exception);
				kickPlayer(player);
			}
		}));
	}

	private void kickPlayer(Player player) {
		player.kickPlayer(ChatColor.RED + messageHandler.get(player, "game.lobby-error"));
	}
}