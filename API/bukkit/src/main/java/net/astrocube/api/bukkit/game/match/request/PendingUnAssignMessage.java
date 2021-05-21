package net.astrocube.api.bukkit.game.match.request;

import net.astrocube.api.core.message.Message;

@Message.ChannelName("gc-pending-unassign")
public interface PendingUnAssignMessage extends MatchActionMessage {

	String getUser();

}
