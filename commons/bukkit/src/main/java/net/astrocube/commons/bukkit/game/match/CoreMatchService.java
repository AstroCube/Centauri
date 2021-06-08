package net.astrocube.commons.bukkit.game.match;

import com.google.inject.Inject;
import net.astrocube.api.bukkit.game.match.ActualMatchCache;
import net.astrocube.api.bukkit.game.match.MatchService;
import net.astrocube.api.bukkit.game.match.MatchSubscription;
import net.astrocube.api.bukkit.game.matchmaking.MatchAssignable;
import net.astrocube.api.bukkit.virtual.game.match.Match;
import net.astrocube.api.bukkit.virtual.game.match.MatchDoc;
import net.astrocube.api.core.service.update.UpdateService;

import java.util.Set;

public class CoreMatchService implements MatchService {

	private @Inject UpdateService<Match, MatchDoc.Partial> matchUpdateService;
	private @Inject ActualMatchCache actualMatchCache;

	@Override
	public void assignSpectator(Match match, String requester, boolean join) throws Exception {
		// TODO: This should probably be two separate methods!
		if (join) {
			// update requester subscription
			actualMatchCache.updateSubscription(
					requester,
					new MatchSubscription(
							match.getId(),
							MatchSubscription.Type.SPECTATOR
					)
			);
			match.getSpectators().add(requester);
		} else {
			match.getSpectators().remove(requester);
		}
		matchUpdateService.updateSync(match);
	}

	@Override
	public void assignTeams(Match match, Set<MatchDoc.Team> teams) throws Exception {
		for (MatchDoc.Team team : teams) {
			for (MatchDoc.TeamMember member : team.getMembers()) {
				actualMatchCache.updateSubscription(
						member.getUser(),
						new MatchSubscription(
								match.getId(),
								MatchSubscription.Type.PLAYER
						)
				);
			}
		}
		match.setTeams(teams);
		matchUpdateService.updateSync(match);
	}

	@Override
	public void unAssignPending(String user, String match) throws Exception {
		// TODO: This calls the backend unassignation, but the method caller already does it. We should move the caller logic here
	}

	@Override
	public void assignPending(MatchAssignable pendingRequest, String match) throws Exception {
		// TODO: This calls the backend assignation, but the method caller already does it. We should move the caller logic here
	}

	@Override
	public void matchCleanup() throws Exception {
		// TODO: (redis) Check what matches are assigned to current server, if its status is LOBBY, delete, else, set status to INVALIDATED and update
	}

	@Override
	public void assignVictory(Match match, Set<String> winners) throws Exception {
		match.getTeams().forEach(team ->
			team.getMembers().forEach(member ->
				member.setActive(false)));
		match.setWinner(winners);
		match.setStatus(MatchDoc.Status.FINISHED);
		matchUpdateService.updateSync(match);
	}

	@Override
	public void disqualify(String match, String user) throws Exception {
		// TODO: This calls the backend disqualification, but the method caller already does it. We should move the caller logic here
	}

	@Override
	public void privatizeMatch(Match match, String requester) throws Exception {
		if (match.isPrivate()) {
			match.setPrivate(false);
			match.setPrivatizedBy(null);
		} else {
			match.setPrivate(true);
			match.setPrivatizedBy(requester);
		}
		matchUpdateService.updateSync(match);
	}

}
