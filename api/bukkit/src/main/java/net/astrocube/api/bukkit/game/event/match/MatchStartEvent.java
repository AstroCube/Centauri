package net.astrocube.api.bukkit.game.event.match;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

@Getter
@AllArgsConstructor
public class MatchStartEvent extends Event {

	private final static HandlerList HANDLER_LIST = new HandlerList();
	private final String match;

	@Override
	public HandlerList getHandlers() {
		return HANDLER_LIST;
	}

	public static HandlerList getHandlerList() {
		return HANDLER_LIST;
	}

}