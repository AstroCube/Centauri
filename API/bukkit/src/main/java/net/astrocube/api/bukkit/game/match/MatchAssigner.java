package net.astrocube.api.bukkit.game.match;

import net.astrocube.api.bukkit.game.matchmaking.MatchAssignable;
import net.astrocube.api.bukkit.virtual.game.match.Match;
import net.astrocube.api.core.virtual.user.User;
import org.bukkit.entity.Player;

/**
 * Assign certain group of {@link User} to a {@link Match}.
 */
public interface MatchAssigner {

	/**
	 * Create a record to be assigned by another server on
	 * player join
	 * @param assignable users to the match
	 * @param match      to be assigned
	 */
	void assign(MatchAssignable assignable, Match match) throws Exception;

	void unAssign(Player player) throws Exception;

	void setRecord(String id, String matchId, String server) throws Exception;

}
