package net.astrocube.api.bukkit.game.match;

import net.astrocube.api.bukkit.virtual.game.match.Match;
import net.astrocube.api.bukkit.virtual.game.match.MatchDoc;

public interface MatchStateUpdater {

    /**
     * Update certain match at the database and emit an event
     * to make bukkit know about the update.
     * @param match to be updated
     * @param status to be updated
     */
    void updateMatch(Match match, MatchDoc.Status status) throws Exception;

}