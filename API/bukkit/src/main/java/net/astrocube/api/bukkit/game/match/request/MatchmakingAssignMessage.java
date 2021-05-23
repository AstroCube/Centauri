package net.astrocube.api.bukkit.game.match.request;

import net.astrocube.api.bukkit.game.matchmaking.MatchAssignable;
import net.astrocube.api.core.message.Message;
import net.astrocube.api.core.virtual.user.User;

@Message.ChannelName("gc-pending-assign")
public interface MatchmakingAssignMessage extends MatchActionMessage {

	/**
	 * @return {@link User} id to assign as spectator.
	 */
	MatchAssignable getAssignable();

}
