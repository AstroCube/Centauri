package net.astrocube.commons.bukkit.game.match.lobby;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import me.yushust.message.MessageHandler;
import net.astrocube.api.bukkit.game.lobby.LobbyScoreboardAssigner;
import net.astrocube.api.bukkit.game.lobby.LobbySessionModifier;
import net.astrocube.api.bukkit.virtual.game.match.Match;
import net.astrocube.api.bukkit.virtual.game.match.MatchDoc;
import net.astrocube.api.core.virtual.gamemode.SubGameMode;
import net.astrocube.api.core.virtual.user.User;
import net.astrocube.commons.bukkit.game.match.control.CoreMatchParticipantsProvider;
import net.astrocube.commons.bukkit.utils.TeleportUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.Set;

@Singleton
public class CoreLobbySessionModifier implements LobbySessionModifier {

	private @Inject MessageHandler messageHandler;
	private @Inject LobbyScoreboardAssigner lobbyAssignerScoreboard;
	private @Inject Plugin plugin;

	@Override
	public void ensureJoin(User user, Player player, Match match, SubGameMode subGameMode) {
		TeleportUtils.loadChunkAndTeleport(player, LobbyLocationParser.getLobby(), plugin);
		player.setGameMode(org.bukkit.GameMode.ADVENTURE);
		player.setHealth(20);
		player.setFoodLevel(20);

		Set<String> waitingIds = CoreMatchParticipantsProvider.getPendingIds(match);

		Bukkit.getOnlinePlayers().forEach(online -> {
			if (waitingIds.contains(online.getDatabaseIdentifier())) {

				if (match.getStatus() == MatchDoc.Status.LOBBY) {
					lobbyAssignerScoreboard.assignLobbyScoreboard(player, match, subGameMode);
				}

				online.sendMessage(
					messageHandler.getMessage("game.lobby-join")
						.replace("%player%", player.getDisplayName())
						.replace("%actual%", waitingIds.size() + "")
						.replace("%max%", subGameMode.getMaxPlayers() + "")
				);

				online.showPlayer(player);
				player.showPlayer(online);

			} else {
				online.hidePlayer(player);
				player.hidePlayer(online);
			}
		});

	}

	@Override
	public void ensureDisconnect(User user, Player player, Match match, SubGameMode subGameMode) {
		Set<String> waitingIds = CoreMatchParticipantsProvider.getPendingIds(match);
		Bukkit.getOnlinePlayers().forEach(online -> {
			if (waitingIds.contains(online.getDatabaseIdentifier())) {
				online.sendMessage(
					messageHandler.getMessage("game.lobby-leave")
						.replace("%player%", player.getDisplayName())
						.replace("%actual%", waitingIds.size() + "")
						.replace("%max%", subGameMode.getMaxPlayers() + "")
				);
			}
		});
	}
}