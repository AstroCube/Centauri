package net.astrocube.api.bukkit.game.matchmaking;

import com.google.inject.Inject;
import me.yushust.message.MessageHandler;
import net.astrocube.api.bukkit.party.PartyService;
import net.astrocube.api.bukkit.teleport.ServerTeleportRetry;
import net.astrocube.api.bukkit.translation.mode.AlertModes;
import net.astrocube.api.core.service.find.FindService;
import net.astrocube.api.core.virtual.gamemode.GameMode;
import net.astrocube.api.core.virtual.gamemode.SubGameMode;
import net.astrocube.api.core.virtual.party.Party;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Level;

public class MatchmakingRequester {

	private @Inject MatchmakingGenerator matchmakingGenerator;
	private @Inject Plugin plugin;
	private @Inject FindService<GameMode> findService;
	private @Inject MessageHandler messageHandler;
	private @Inject ServerTeleportRetry serverTeleportRetry;
	private @Inject PartyService partyService;

	public void execute(Player player, String mode, String subMode) {
		System.out.println("Matchmaking requester");
		findService.find(mode).callback(response -> {

			response.ifSuccessful(gameMode -> {

				if (subMode.isEmpty()) {
					System.out.println("SubMode is empty");
					serverTeleportRetry.attemptGroupTeleport(
						player.getName(),
						gameMode.getLobby(),
						1,
						3
					);
					return;
				}

				Optional<Party> optional = partyService.getPartyOf(player.getDatabaseId());
				Set<String> involved = new HashSet<>();

				if (optional.isPresent()) {
					if (!optional.get().getLeader().equals(player.getDatabaseId())) {
						messageHandler.send(player, "no-leader-party-for-select-game");
						return;
					}
					involved = optional.get().getMembers();
				}

				if (gameMode.getSubTypes() != null) {
					System.out.println("Subtypes no is null");
					Optional<SubGameMode> subGameMode = gameMode.getSubTypes()
						.stream()
						.filter(g -> g.getId().equalsIgnoreCase(subMode))
						.findAny();

					Set<String> finalInvolved = involved;
					subGameMode.ifPresent(subModeRecord -> {

						try {
							System.out.println("MatchmakingGenerator");
							matchmakingGenerator.pairMatch(player, finalInvolved, gameMode, subModeRecord);
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
