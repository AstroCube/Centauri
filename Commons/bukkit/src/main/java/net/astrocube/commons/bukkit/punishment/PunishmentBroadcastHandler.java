package net.astrocube.commons.bukkit.punishment;

import net.astrocube.api.bukkit.punishment.event.PunishmentIssueEvent;
import net.astrocube.api.core.message.MessageListener;
import net.astrocube.api.core.message.MessageMetadata;
import net.astrocube.api.core.virtual.punishment.Punishment;
import org.bukkit.Bukkit;

public class PunishmentBroadcastHandler implements MessageListener<Punishment> {

	@Override
	public void handleDelivery(Punishment message, MessageMetadata properties) {
		Bukkit.getPluginManager().callEvent(new PunishmentIssueEvent(message));
	}

}
