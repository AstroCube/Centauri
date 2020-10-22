package net.astrocube.api.bukkit.game.match.control;

import net.astrocube.api.bukkit.virtual.game.match.Match;

/**
 * Interface to define a certain {@link Match} scheduler
 * that can generate one or many matches and
 * emit results depending on its creation.
 */
public interface MatchScheduler {

    /**
     * Run the {@link Match} scheduler.
     */
    void schedule();

}
