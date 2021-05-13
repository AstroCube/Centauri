package net.astrocube.commons.bukkit.game.match;

import net.astrocube.api.bukkit.game.event.match.MatchAssignationEvent;
import net.astrocube.api.bukkit.game.matchmaking.SingleMatchAssignation;
import net.astrocube.api.core.message.MessageHandler;
import net.astrocube.api.core.message.Metadata;
import org.bukkit.Bukkit;

public class MatchAssignationHandler implements MessageHandler<SingleMatchAssignation> {

	@Override
	public Class<SingleMatchAssignation> type() {
		return SingleMatchAssignation.class;
	}

	@Override
	public void handleDelivery(SingleMatchAssignation message, Metadata properties) {
		Bukkit.getPluginManager().callEvent(new MatchAssignationEvent(message));
	}

}
