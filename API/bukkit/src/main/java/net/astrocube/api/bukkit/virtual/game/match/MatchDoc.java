package net.astrocube.api.bukkit.virtual.game.match;

import com.fasterxml.jackson.annotation.JsonProperty;
import net.astrocube.api.core.model.Model;
import net.astrocube.api.core.model.PartialModel;
import net.astrocube.api.core.service.create.CreateService;
import net.astrocube.api.core.service.find.FindService;
import net.astrocube.api.core.virtual.gamemode.GameMode;
import net.astrocube.api.core.virtual.gamemode.SubGameMode;
import net.astrocube.api.core.virtual.user.User;
import org.bukkit.Color;

import java.util.Set;

/**
 * Database schema model which stores all the
 * information about a Game Control match.
 */
public interface MatchDoc {

    /**
     * Empty interface that acts as placeholder for
     * Model Services.
     */
    interface Partial extends PartialModel {}

    /**
     * Base identity that can be used to create
     * a Match with {@link CreateService}
     */
    interface Identity extends Partial {

        /**
         * @return map id to be used with a {@link FindService}
         */
        String getMap();

        /**
         * @return server id to be used with a {@link FindService}
         */
        String getServer();

        /**
         * @return certain {@link Status} of the match
         */
        Status getStatus();

        /**
         * @return {@link GameMode} id to be used with a {@link FindService}
         */
        @JsonProperty("gamemode")
        String getGameMode();

        /**
         * @return map {@link SubGameMode} id to be filtered from {@link GameMode} registry
         */
        @JsonProperty("subGamemode")
        String getSubMode();

    }

    /**
     * Interface that serves as abstraction for {@link User} related
     * assignation at this match.
     */
    interface Assignation {

        /**
         * @return set of teams assigned to the match.
         */
        Set<Team> getTeams();

        /**
         * @return set of {@link User} ids who are identified as match winners.
         */
        Set<String> getWinner();

    }

    /**
     * Interface that store base team-related data.
     */
    interface Team {

        /**
         * @return name of the team
         */
        String getName();

        /**
         * @return set of {@link User} ids who are identified as team members.
         */
        Set<String> getMembers();

        /**
         * @return {@link Color} in string form.
         */
        String getColor();

    }

    /**
     * Enum with options that can be used to determine a match status.
     */
    enum Status {
        PREPARING, LOBBY, STARTING, RUNNING, FINISHED, INVALIDATED
    }

    /**
     * Complete interface including all the match abstractions.
     */
    interface Complete extends Model.Stamped, Partial, Identity, Assignation {}

}
