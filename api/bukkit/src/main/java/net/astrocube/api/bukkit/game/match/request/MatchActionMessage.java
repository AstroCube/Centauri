package net.astrocube.api.bukkit.game.match.request;

import net.astrocube.api.bukkit.virtual.game.match.Match;
import net.astrocube.api.core.message.Message;

public interface MatchActionMessage extends Message {

	/**
	 * @return {@link Match} id to assign the user.
	 */
	String getMatch();

}
