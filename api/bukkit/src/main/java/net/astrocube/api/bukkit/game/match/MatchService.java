package net.astrocube.api.bukkit.game.match;

import net.astrocube.api.bukkit.game.matchmaking.MatchAssignable;
import net.astrocube.api.bukkit.virtual.game.match.Match;
import net.astrocube.api.bukkit.virtual.game.match.MatchDoc;

import java.util.Set;

public interface MatchService {

	/**
	 * Assign an spectator to an ongoing match.
	 * @param user  id to be assigned
	 * @param match id to be added
	 * @param join  or leave
	 * @throws Exception when a backend error is thrown
	 */
	void assignSpectator(Match match, String user, boolean join) throws Exception;

	/**
	 * Assign a set of teams after a match has started
	 * @param teams to be assigned
	 * @param match to be added
	 * @throws Exception when a backend error is thrown
	 */
	void assignTeams(Match match, Set<MatchDoc.Team> teams) throws Exception;

	/**
	 * UnAssign a pending user from its {@link MatchAssignable} and perform
	 * certain power-balance things at backend.
	 * @param user  to be removed
	 * @param match id to be added
	 * @throws Exception when a backend error is thrown
	 */
	void unAssignPending(String user, String match) throws Exception;

	/**
	 * Assign a pending matchmaking request {@link MatchAssignable} to the
	 * waiting lobby.
	 * @param pendingRequest to be assigned at the match
	 * @param match          id to be assigned
	 * @throws Exception when a backend error is thrown
	 */
	void assignPending(MatchAssignable pendingRequest, String match) throws Exception;

	/**
	 * Clears every match still waiting or invalidate active ones if the server shut down.
	 * @throws Exception when a backend error is thrown
	 */
	void matchCleanup() throws Exception;

	/**
	 * Performs a victory state and updates the match with final winners.
	 * @param match   to be updated
	 * @param winners to be listed
	 */
	void assignVictory(Match match, Set<String> winners) throws Exception;

	/**
	 * Disqualify a user from a match marking him as "not active".
	 * @param match to be queried
	 * @param user  to be disauqlified
	 */
	void disqualify(String match, String user) throws Exception;

	void privatizeMatch(Match match, String requester) throws Exception;

}