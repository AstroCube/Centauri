package net.astrocube.api.bukkit.game.matchmaking;

import net.astrocube.api.core.virtual.user.User;

import java.beans.ConstructorProperties;
import java.util.Set;

/**
 * Wrapper to certain group of {@link User} who can be assigned
 * to a match by the {@link MatchmakingRegistryHandler}
 */
public class MatchAssignable {

	private final String responsible;
	private final Set<String> involved;

	@ConstructorProperties({"responsible", "involved"})
	public MatchAssignable(String responsible, Set<String> involved) {
		this.responsible = responsible;
		this.involved = involved;
	}

	/**
	 * @return {@link User} id of the assignable
	 * responsible. Can be just one in case of
	 * SOLO playing.
	 */
	public String getResponsible() {
		return responsible;
	}

	/**
	 * @return involved {@link User}s id excluding
	 * the responsible. Generally used in non SOLO
	 * matchmaking.
	 */
	public Set<String> getInvolved() {
		return involved;
	}

}
