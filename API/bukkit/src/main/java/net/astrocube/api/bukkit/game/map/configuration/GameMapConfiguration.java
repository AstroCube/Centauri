package net.astrocube.api.bukkit.game.map.configuration;

import net.astrocube.api.bukkit.virtual.game.match.MatchDoc;
import net.astrocube.api.core.virtual.gamemode.GameMode;
import org.bukkit.Color;

import java.util.Set;

/**
 * Base interface in order to homogenize all the
 * {@link GameMode} configurations.
 */
public interface GameMapConfiguration {

    /**
     * @return spectator point where users will be spawned.
     */
    CoordinatePoint getSpectatorSpawn();

    /**
     * @return base teams to be used at {@link MatchDoc.Team} balancing.
     */
    Set<? extends MapTeam> getTeams();

    interface MapTeam {

        /**
         * @return name of the team
         */
        String getName();

        /**
         * @return {@link Color} in string form.
         */
        String getColor();

    }

}
