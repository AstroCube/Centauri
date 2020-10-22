package net.astrocube.api.bukkit.game.matchmaking;

import net.astrocube.api.core.virtual.user.User;

import java.util.Set;

/**
 * Wrapper to certain group of {@link User} who can be assigned
 * to a match by the {@link MatchmakingRegistryHandler}
 */
public interface MatchAssignable {

    /**
     * @return {@link User} id of the assignable
     * responsible. Can be just one in case of
     * SOLO playing.
     */
    String getResponsible();

    /**
     * @return involved {@link User}s id excluding
     * the responsible. Generally used in non SOLO
     * matchmaking.
     */
    Set<String> getInvolved();

}
