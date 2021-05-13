package net.astrocube.commons.bukkit.punishment;

import net.astrocube.api.bukkit.punishment.event.PunishmentIssueEvent;
import net.astrocube.api.core.message.MessageHandler;
import net.astrocube.api.core.message.Metadata;
import net.astrocube.api.core.virtual.punishment.Punishment;
import org.bukkit.Bukkit;

public class PunishmentBroadcastHandler implements MessageHandler<Punishment> {

	@Override
	public Class<Punishment> type() {
		return Punishment.class;
	}

	@Override
	public void handleDelivery(Punishment message, Metadata properties) {
		Bukkit.getPluginManager().callEvent(new PunishmentIssueEvent(message));
	}

}
