package net.astrocube.api.bukkit.punishment.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.astrocube.api.core.virtual.punishment.Punishment;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

@Getter
@AllArgsConstructor
public class PunishmentIssueEvent extends Event {

	private final static HandlerList HANDLER_LIST = new HandlerList();
	private final Punishment punishment;

	@Override
	public HandlerList getHandlers() {
		return HANDLER_LIST;
	}

	public static HandlerList getHandlerList() {
		return HANDLER_LIST;
	}

}