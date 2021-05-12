package net.astrocube.api.bukkit.game.match.request;

import net.astrocube.api.bukkit.virtual.game.match.Match;
import net.astrocube.api.bukkit.virtual.game.match.MatchDoc;
import net.astrocube.api.core.message.Message;
import net.astrocube.api.core.message.MessageDefaults;

import java.util.Set;

@MessageDefaults.ChannelName("gc-assign-teams")
public interface TeamAssignMessage extends MatchActionMessage {

	/**
	 * @return {@link Match} teams to register at the match.
	 */
	Set<MatchDoc.Team> getTeams();

}
