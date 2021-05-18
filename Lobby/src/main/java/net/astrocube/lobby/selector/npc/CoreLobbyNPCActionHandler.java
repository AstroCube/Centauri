package net.astrocube.lobby.selector.npc;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import me.yushust.message.MessageHandler;
import net.astrocube.api.bukkit.game.matchmaking.MatchmakingGenerator;
import net.astrocube.api.bukkit.lobby.selector.npc.LobbyNPCActionHandler;
import net.astrocube.api.bukkit.teleport.ServerTeleportRetry;
import net.astrocube.api.bukkit.translation.mode.AlertModes;
import net.astrocube.api.core.service.find.FindService;
import net.astrocube.api.core.virtual.gamemode.GameMode;
import net.astrocube.api.core.virtual.gamemode.SubGameMode;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.Optional;
import java.util.logging.Level;

@Singleton
public class CoreLobbyNPCActionHandler implements LobbyNPCActionHandler {

	private @Inject MatchmakingGenerator matchmakingGenerator;
	private @Inject Plugin plugin;
	private @Inject FindService<GameMode> findService;
	private @Inject MessageHandler messageHandler;
	private @Inject ServerTeleportRetry serverTeleportRetry;

	@Override
	public void execute(Player player, String mode, String subMode) {

		System.out.println("Execute");

		findService.find(mode).callback(response -> {

			response.ifSuccessful(gameMode -> {

				if (subMode.isEmpty()) {
					serverTeleportRetry.attemptGroupTeleport(
						player.getName(),
						gameMode.getLobby(),
						1,
						3
					);
					return;
				}


				if (gameMode.getSubTypes() != null) {

					Optional<SubGameMode> subGameMode = gameMode.getSubTypes()
						.stream()
						.filter(g -> g.getId().equalsIgnoreCase(subMode))
						.findAny();

					subGameMode.ifPresent(subModeRecord -> {

						try {
							System.out.println("MatchmakingGenerator");
							matchmakingGenerator.pairMatch(player, gameMode, subModeRecord);
						} catch (Exception e) {
							messageHandler.sendIn(player, AlertModes.ERROR, "selectors.error");
							plugin.getLogger().log(Level.SEVERE, "There was an error pairing to match", e);
						}

					});

				}

			});

		});

	}

}
