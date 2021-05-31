package net.astrocube.api.bukkit.game.match;

import net.astrocube.api.bukkit.game.exception.GameControlException;
import net.astrocube.api.bukkit.virtual.game.match.Match;
import net.astrocube.api.core.virtual.user.User;
import org.bukkit.entity.Player;

public interface UserMatchJoiner {

	/**
	 * Determines the nature of the user's income,
	 * either as a spectator or as a player.
	 * @param user   to be processed
	 * @param player to be processed
	 */
	void processJoin(User user, Player player) throws Exception;

	enum Origin {
		WAITING, PLAYING, SPECTATING
	}

	static Origin convertSubscriptionToOrigin(MatchSubscription.Type subscriptionType) {
		switch (subscriptionType) {
			case SPECTATOR: return Origin.SPECTATING;
			case PLAYER: return Origin.PLAYING;
			default: return Origin.WAITING;
		}
	}

	static Origin checkOrigin(String user, Match match) throws GameControlException {

		if (match.getSpectators().contains(user)) {
			return Origin.SPECTATING;
		}

		if (match.getTeams().stream().anyMatch(m -> m.getMembers().stream().anyMatch(teamMember ->
			teamMember.getUser().equalsIgnoreCase(user)))
		) {
			return Origin.PLAYING;
		} else if (match.getPending().stream().anyMatch(m -> m.getInvolved().contains(user)
			|| m.getResponsible().equalsIgnoreCase(user))) {
			return Origin.WAITING;
		}

		throw new GameControlException("There was no assignation found for this user");
	}

}
