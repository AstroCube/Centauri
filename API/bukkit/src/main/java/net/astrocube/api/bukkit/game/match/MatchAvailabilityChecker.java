package net.astrocube.api.bukkit.game.match;

public interface MatchAvailabilityChecker {

    /**
     * Check if found matches are still alive or bricked
     * in any playable state.
     * @param id of user to search
     * @return if has a legit match
     * @throws Exception when backend communication failed
     */
    void  clearLegitMatches(String id) throws Exception;

}
