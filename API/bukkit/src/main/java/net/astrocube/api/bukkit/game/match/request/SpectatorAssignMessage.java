package net.astrocube.api.bukkit.game.match.request;

import net.astrocube.api.core.message.MessageDefaults;
import net.astrocube.api.core.virtual.user.User;

@MessageDefaults.ChannelName("gc-assign-spectator")
public interface SpectatorAssignMessage extends MatchActionMessage {

	/**
	 * @return {@link User} id to assign as spectator.
	 */
	String getUser();

	/**
	 * @return if user join or leaves
	 */
	boolean isJoin();

}
