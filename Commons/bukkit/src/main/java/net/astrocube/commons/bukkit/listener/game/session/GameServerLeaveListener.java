package net.astrocube.commons.bukkit.listener.game.session;

import com.google.inject.Inject;
import me.yushust.message.MessageHandler;
import net.astrocube.api.bukkit.game.event.game.GameUserDisconnectEvent;
import net.astrocube.api.bukkit.game.match.MatchService;
import net.astrocube.api.bukkit.game.match.UserMatchJoiner;
import net.astrocube.api.bukkit.virtual.game.match.Match;
import net.astrocube.api.core.service.find.FindService;
import net.astrocube.api.core.virtual.gamemode.GameMode;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

import java.util.logging.Level;

public class GameServerLeaveListener implements Listener {

	private @Inject FindService<Match> findService;
	private @Inject FindService<GameMode> gameModeFindService;
	private @Inject MessageHandler messageHandler;
	private @Inject MatchService matchService;
	private @Inject Plugin plugin;

	@EventHandler
	public void onGameUserDisconnect(GameUserDisconnectEvent event) {

		findService.find(event.getMatch()).callback(matchResponse -> {

			matchResponse.ifSuccessful(match -> {

				if (event.getOrigin() == UserMatchJoiner.Origin.PLAYING) {

					gameModeFindService.find(match.getGameMode()).callback(gamemodeResponse -> {

						gamemodeResponse.ifSuccessful(mode -> {

							if (mode.getSubTypes() != null) {

								mode.getSubTypes().stream().filter(sub -> sub.getId().equalsIgnoreCase(match.getSubMode()) && !sub.hasRejoin())
									.findAny()
									.ifPresent(sub -> {
										try {
											matchService.disqualify(match.getId(), event.getPlayer().getDatabaseIdentifier());
										} catch (Exception exception) {
											plugin.getLogger().log(Level.SEVERE, "Error while disqualifying player", exception);
										}
									});
							}

						});

					});

				}

			});

		});


	}

	private void kickPlayer(Player player) {
		player.kickPlayer(ChatColor.RED + messageHandler.get(player, "game.lobby-error"));
	}

}
