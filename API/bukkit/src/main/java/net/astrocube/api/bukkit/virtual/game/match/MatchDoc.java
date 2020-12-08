package net.astrocube.api.bukkit.virtual.game.match;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.node.ObjectNode;
import net.astrocube.api.bukkit.game.matchmaking.MatchAssignable;
import net.astrocube.api.bukkit.game.matchmaking.MatchmakingRequest;
import net.astrocube.api.core.model.Model;
import net.astrocube.api.core.model.PartialModel;
import net.astrocube.api.core.service.create.CreateService;
import net.astrocube.api.core.service.find.FindService;
import net.astrocube.api.core.virtual.gamemode.GameMode;
import net.astrocube.api.core.virtual.gamemode.SubGameMode;
import net.astrocube.api.core.virtual.user.User;
import org.bukkit.Color;

import java.util.Optional;
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
         * @param map to be updated
         */
        void setMap(String map);

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
         * @return server id to be used with a {@link FindService}
         */
        String getServer();

        /**
         * @return set of teams assigned to the match.
         */
        Set<Team> getTeams();

        /**
         * @return set of {@link User} ids who are identified as match winners.
         */
        Set<String> getWinner();

        /**
         * @return set of {@link User} ids who are actually spectating the match
         * without an specified team. (Can be from outside like moderators)
         */
        Set<String> getSpectators();

        /**
         * @return set of {@link MatchAssignable}s that are waiting to the
         * match start cycle.
         */
        Set<MatchAssignable> getPending();

        /**
         * @return query of detailed matchmaking.
         */
        Optional<ObjectNode> getQuery();

        /**
         * @return {@link MatchmakingRequest} responsible who requested
         * a detailed matchmaking.
         */
        Optional<String> getRequestedBy();


        /**
         * @return certain {@link Status} of the match
         */
        Status getStatus();

        /**
         * @param status to update match property
         */
        void setStatus(Status status);

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
        @JsonProperty("Preparing")
        PREPARING,
        @JsonProperty("Lobby")
        LOBBY,
        @JsonProperty("Starting")
        STARTING,
        @JsonProperty("Running")
        RUNNING,
        @JsonProperty("Finished")
        FINISHED,
        @JsonProperty("Invalidated")
        INVALIDATED
    }

    /**
     * Complete interface including all the match abstractions.
     */
    interface Complete extends Model.Stamped, Partial, Identity, Assignation {}

}
