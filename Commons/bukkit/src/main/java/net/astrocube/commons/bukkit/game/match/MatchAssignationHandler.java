package net.astrocube.commons.bukkit.game.match;

import net.astrocube.api.bukkit.game.event.match.MatchAssignationEvent;
import net.astrocube.api.bukkit.game.matchmaking.SingleMatchAssignation;
import net.astrocube.api.core.message.MessageListener;
import net.astrocube.api.core.message.MessageMetadata;
import org.bukkit.Bukkit;

public class MatchAssignationHandler implements MessageListener<SingleMatchAssignation> {

	@Override
	public void handleDelivery(SingleMatchAssignation message, MessageMetadata properties) {
		Bukkit.getPluginManager().callEvent(new MatchAssignationEvent(message));
	}

}
