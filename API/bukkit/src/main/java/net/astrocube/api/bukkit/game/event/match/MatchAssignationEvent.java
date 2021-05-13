package net.astrocube.api.bukkit.game.event.match;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.astrocube.api.bukkit.game.matchmaking.SingleMatchAssignation;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

@Getter
@AllArgsConstructor
public class MatchAssignationEvent extends Event {

	private final static HandlerList HANDLER_LIST = new HandlerList();
	private final SingleMatchAssignation assignation;

	@Override
	public HandlerList getHandlers() {
		return HANDLER_LIST;
	}

	public static HandlerList getHandlerList() {
		return HANDLER_LIST;
	}

}
