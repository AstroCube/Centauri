package net.astrocube.commons.bukkit.game.match;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.astrocube.api.bukkit.game.match.ActualMatchCache;
import net.astrocube.api.bukkit.game.matchmaking.MatchAssignable;
import net.astrocube.api.bukkit.virtual.game.match.Match;
import net.astrocube.api.bukkit.virtual.game.match.MatchDoc;
import net.astrocube.api.core.service.find.FindService;
import net.astrocube.api.core.service.update.UpdateService;
import net.astrocube.api.core.virtual.user.User;
import net.astrocube.api.core.virtual.user.UserDoc;
import net.astrocube.api.core.virtual.user.part.MatchSubscription;

import java.util.Optional;

@Singleton
public class CoreActualMatchCache implements ActualMatchCache {

	private @Inject FindService<Match> matchFindService;
	private @Inject FindService<User> userFindService;
	private @Inject UpdateService<User, UserDoc.Partial> userUpdateService;

	@Override
	public void updateSubscription(Match match, MatchAssignable assignable) throws Exception {
		// update the responsible
		User responsible = userFindService.findSync(assignable.getResponsible());
		responsible.getSession().setMatchSubscription(new MatchSubscription(
			match.getId(),
			MatchSubscription.Type.ASSIGNATION_RESPONSIBLE
		));
		userUpdateService.update(responsible);

		// update the involved users
		for (String involvedId : assignable.getInvolved()) {
			User involved = userFindService.findSync(involvedId);
			involved.getSession().setMatchSubscription(new MatchSubscription(
				match.getId(),
				MatchSubscription.Type.ASSIGNATION_INVOLVED
			));
			userUpdateService.update(involved);
		}
	}

	@Override
	public Optional<Match> get(User user) throws Exception {
		MatchSubscription subscription = user.getSession().getMatchSubscription();
		// no subscription, no match
		if (subscription == null) {
			return Optional.empty();
		} else {
			Match match = matchFindService.findSync(subscription.getMatch());
			if (match == null
				|| match.getStatus() == MatchDoc.Status.FINISHED
				|| match.getStatus() == MatchDoc.Status.INVALIDATED) {
				return Optional.empty();
			} else {
				return Optional.of(match);
			}
		}
	}

	@Override
	public Optional<Match> get(String id) throws Exception {
		return get(userFindService.findSync(id));
	}

	private void clearSubscription(String userId) throws Exception {
		User user = userFindService.findSync(userId);
		user.getSession().setMatchSubscription(null);
		userUpdateService.updateSync(user);
	}

	@Override
	public void clearSubscriptions(Match match) throws Exception {
		for (String spectatorId : match.getSpectators()) clearSubscription(spectatorId);
		for (MatchDoc.Team team : match.getTeams()) {
			for (MatchDoc.TeamMember member : team.getMembers()) {
				if (member.isActive()) {
					clearSubscription(member.getUser());
				}
			}
		}
		for (MatchAssignable assignable : match.getPending()) {
			clearSubscription(assignable.getResponsible());
			for (String involved : assignable.getInvolved()) {
				clearSubscription(involved);
			}
		}
	}
}
