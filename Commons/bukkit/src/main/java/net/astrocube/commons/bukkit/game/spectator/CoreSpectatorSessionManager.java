package net.astrocube.commons.bukkit.game.spectator;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.astrocube.api.bukkit.game.exception.GameControlException;
import net.astrocube.api.bukkit.game.map.GameMapCache;
import net.astrocube.api.bukkit.game.map.MapConfigurationProvider;
import net.astrocube.api.bukkit.game.map.configuration.CoordinatePoint;
import net.astrocube.api.bukkit.game.map.configuration.GameMapConfiguration;
import net.astrocube.api.bukkit.game.spectator.SpectatorSessionManager;
import net.astrocube.api.bukkit.virtual.game.match.Match;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

@Singleton
public class CoreSpectatorSessionManager implements SpectatorSessionManager {

    private @Inject MapConfigurationProvider mapConfigurationProvider;
    private @Inject GameMapCache gameMapCache;

    @Override
    public void provideFunctions(Player player, Match match) throws GameControlException, JsonProcessingException {


        CoordinatePoint configuration = mapConfigurationProvider.parseConfiguration(
                new String(gameMapCache.getConfiguration(match.getMap())),
                GameMapConfiguration.class
        ).getSpectatorSpawn();

        World world = Bukkit.getWorld("match_" + match.getId());

        if  (world == null) {
            throw new GameControlException("Match world could not be found");
        }

        player.teleport(new Location(world, configuration.getX(), configuration.getY(), configuration.getZ()));

    }

}
