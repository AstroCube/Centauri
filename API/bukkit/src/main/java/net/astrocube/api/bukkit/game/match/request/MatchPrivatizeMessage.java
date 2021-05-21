package net.astrocube.api.bukkit.game.match.request;

import net.astrocube.api.core.message.Message;
import net.astrocube.api.core.virtual.user.User;

@Message.ChannelName("gc-privatization")
public interface MatchPrivatizeMessage extends MatchActionMessage {

	/**
	 * @return {@link User} to request privatizing.
	 */
	String getRequester();

}
