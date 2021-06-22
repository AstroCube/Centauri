package net.astrocube.commons.bukkit.game.spectator;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.astrocube.api.bukkit.game.exception.GameControlException;
import net.astrocube.api.bukkit.game.map.GameMapCache;
import net.astrocube.api.bukkit.game.map.MapConfigurationProvider;
import net.astrocube.api.bukkit.game.map.configuration.CoordinatePoint;
import net.astrocube.api.bukkit.game.map.configuration.GameMapConfiguration;
import net.astrocube.api.bukkit.game.match.control.MatchParticipantsProvider;
import net.astrocube.api.bukkit.game.spectator.GhostEffectControl;
import net.astrocube.api.bukkit.game.spectator.LobbyItemProvider;
import net.astrocube.api.bukkit.game.spectator.SpectatorSessionManager;
import net.astrocube.api.bukkit.virtual.game.match.Match;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.util.Set;

@Singleton
public class CoreSpectatorSessionManager implements SpectatorSessionManager {

	private @Inject MapConfigurationProvider mapConfigurationProvider;
	private @Inject GameMapCache gameMapCache;
	private @Inject LobbyItemProvider lobbyItemProvider;
	private @Inject GhostEffectControl ghostEffectControl;

	@Override
	public void provideFunctions(Player player, Match match) throws GameControlException, IOException {


		CoordinatePoint configuration = mapConfigurationProvider.parseConfiguration(
			new String(gameMapCache.getConfiguration(match.getMap())),
			GameMapConfiguration.class
		).getSpectatorSpawn();

		World world = Bukkit.getWorld("match_" + match.getId());

		if (world == null) {
			throw new GameControlException("Match world could not be found");
		}

		player.getInventory().clear();
		player.getInventory().setHelmet(null);
		player.getInventory().setChestplate(null);
		player.getInventory().setLeggings(null);
		player.getInventory().setBoots(null);

		lobbyItemProvider.provideBackButton(player, 8);
		ghostEffectControl.addPlayer(match.getId(), player);

		Set<Player> spectatingPlayers =
			MatchParticipantsProvider.getSpectatingPlayers(match);

		for (Player online : Bukkit.getOnlinePlayers()) {

			if (!spectatingPlayers.contains(online)) {
				online.hidePlayer(player);
				continue;
			}

			player.showPlayer(online);
			online.showPlayer(player);
		}


		player.setHealth(20);
		player.setFoodLevel(20);

		player.teleport(new Location(world, configuration.getX(), configuration.getY(), configuration.getZ()));
		player.setAllowFlight(true);
		player.setFlying(true);
	}
}