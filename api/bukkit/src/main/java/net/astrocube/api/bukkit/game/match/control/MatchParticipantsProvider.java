package net.astrocube.api.bukkit.game.match.control;

import net.astrocube.api.bukkit.virtual.game.match.Match;
import net.astrocube.api.bukkit.virtual.game.match.MatchDoc;
import net.astrocube.api.core.virtual.user.User;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Objects;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public interface MatchParticipantsProvider {

	/**
	 * Get all the match pending users
	 * @param match to be accessed
	 * @return provided users
	 */
	Set<User> getMatchPending(Match match);

	/**
	 * Get all the match spectators.
	 * @param match to be accessed
	 * @return provided users
	 */
	Set<User> getMatchSpectators(Match match);

	/**
	 * Get all the match active players.
	 * @param match to be accessed
	 * @return provided users
	 */
	Set<User> getMatchUsers(Match match);

	static Set<Player> getInvolved(Match match) {
		Set<Player> players = getOnlinePlayers(match, MatchDoc.TeamMember::isActive);
		players.addAll(getSpectatingPlayers(match));
		return players;
	}

	static Set<String> getInvolvedIds(Match match) {
		Set<String> players = getOnlineIds(match);
		players.addAll(match.getSpectators());
		return players;
	}

	static Set<Player> getSpectatingPlayers(Match match) {
		return getPlayers(match.getSpectators().stream());
	}

	static Set<Player> getOnlinePlayers(Match match, Predicate<MatchDoc.TeamMember> teamMemberPredicate) {
		return getPlayers(match.getTeams()
			.stream()
			.flatMap(teams ->
				teams.getMembers().stream()
					.filter(teamMemberPredicate)
					.map(MatchDoc.TeamMember::getUser)
			)
		);
	}

	static Set<Player> getPlayersWithFilter(Match match, Predicate<MatchDoc.TeamMember> teamMemberPredicate) {
		return getPlayers(match.getTeams()
			.stream()
			.flatMap(teams ->
				teams.getMembers().stream()
					.filter(teamMemberPredicate)
					.map(MatchDoc.TeamMember::getUser)
			)
		);
	}

	static Set<String> getOnlineIds(Match match) {
		return match
			.getTeams()
			.stream()
			.flatMap(teams ->
				teams.getMembers().stream()
					.filter(MatchDoc.TeamMember::isActive)
					.map(MatchDoc.TeamMember::getUser))
			.collect(Collectors.toSet());
	}

	static Set<String> getWaitingIds(Match match) {
		return match
			.getPending()
			.stream()
			.flatMap(pending -> {
				Set<String> delay = pending.getInvolved();
				delay.add(pending.getResponsible());
				return delay.stream();
			}).collect(Collectors.toSet());
	}

	static Set<Player> getPlayers(Stream<String> ids) {
		return ids.map(Bukkit::getPlayerByIdentifier)
			.filter(Objects::nonNull)
			.collect(Collectors.toSet());
	}
}