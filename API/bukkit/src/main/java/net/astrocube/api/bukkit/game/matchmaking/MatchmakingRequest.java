package net.astrocube.api.bukkit.game.matchmaking;

import com.fasterxml.jackson.databind.JsonNode;
import net.astrocube.api.bukkit.virtual.game.match.Match;
import net.astrocube.api.core.virtual.gamemode.GameMode;
import net.astrocube.api.core.virtual.gamemode.SubGameMode;
import net.astrocube.api.core.virtual.user.User;

import java.util.Date;
import java.util.Set;

/**
 * Matchmaking request to be registered at
 * redis and being adjudicated to a {@link Match}.
 */
public interface MatchmakingRequest {

    /**
     * @return date when request was created.
     */
    Date getIssuedDate();

    /**
     * @return requested {@link GameMode} id.
     */
    String getGameMode();

    /**
     * @return requested {@link SubGameMode} id.
     */
    String getSubGameMode();

    /**
     * @return {@link User} set of people to be paired
     * at a certain match.
     */
    Set<String> getRequesters();

    /**
     * @return JSON criteria where the available matches
     * will be filtered.
     */
    JsonNode getCriteria();

}
