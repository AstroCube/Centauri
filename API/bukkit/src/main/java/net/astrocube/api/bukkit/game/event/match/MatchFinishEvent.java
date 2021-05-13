package net.astrocube.api.bukkit.game.event.match;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.Set;

@Getter
@AllArgsConstructor
public class MatchFinishEvent extends Event {

	private final static HandlerList HANDLER_LIST = new HandlerList();
	private final String match;
	private final Set<String> winners;

	@Override
	public HandlerList getHandlers() {
		return HANDLER_LIST;
	}

	public static HandlerList getHandlerList() {
		return HANDLER_LIST;
	}

}
