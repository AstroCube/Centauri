package net.astrocube.api.bukkit.game.match;

import net.astrocube.api.bukkit.virtual.game.match.Match;

import java.util.Optional;

public interface ActualMatchProvider {

    /**
     * Retrieve actual user match.
     * @param id of user to be retrieved.
     * @return containing match.
     */
    Optional<Match> provide(String id) throws Exception;

}
