package net.astrocube.api.bukkit.board;

import team.unnamed.uboard.ScoreboardManager;

public interface ScoreboardManagerProvider {

    /**
     * Setup scoreboard manager
     */
    void setupManager();

    /**
     * @return scoreboard
     */
    ScoreboardManager getScoreboard();

}
