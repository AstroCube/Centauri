package net.astrocube.commons.bukkit.game.match.control;

import com.google.inject.Inject;
import net.astrocube.api.bukkit.game.match.control.MatchParticipantsProvider;
import net.astrocube.api.bukkit.virtual.game.match.Match;
import net.astrocube.api.bukkit.virtual.game.match.MatchDoc;
import net.astrocube.api.core.service.find.FindService;
import net.astrocube.api.core.virtual.user.User;

import java.util.Collection;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class CoreMatchParticipantsProvider implements MatchParticipantsProvider {

	private @Inject FindService<User> userFindService;

	@Override
	public Set<User> getMatchPending(Match match) {
		return getUsersFromIds(getPendingIds(match));
	}

	@Override
	public Set<User> getMatchSpectators(Match match) {
		return getUsersFromIds(match.getSpectators());
	}

	@Override
	public Set<User> getMatchUsers(Match match) {
		return getUsersFromIds(match.getTeams().stream()
			.flatMap(team -> team.getMembers().stream().map(MatchDoc.TeamMember::getUser))
			.collect(Collectors.toSet()));
	}

	public Set<User> getUsersFromIds(Set<String> users) {
		return users.stream()
			.map(user -> {
				try {
					return userFindService.findSync(user);
				} catch (Exception ignore) {
				}
				return null;
			})
			.filter(Objects::nonNull)
			.collect(Collectors.toSet());
	}

	public static Set<String> getPendingIds(Match match) {
		return match.getPending().stream()
			.map(assignable -> {
				assignable.getInvolved().add(assignable.getResponsible());
				return assignable.getInvolved();
			})
			.flatMap(Collection::stream).collect(Collectors.toSet());
	}
}