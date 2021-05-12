package net.astrocube.api.bukkit.game.event.matchmaking;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.astrocube.api.bukkit.game.matchmaking.MatchmakingRequest;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

@Getter
@AllArgsConstructor
public class MatchmakingTimeoutEvent extends Event {

	private final static HandlerList HANDLER_LIST = new HandlerList();
	private final MatchmakingRequest matchmakingRequest;

	@Override
	public HandlerList getHandlers() {
		return HANDLER_LIST;
	}

	public static HandlerList getHandlerList() {
		return HANDLER_LIST;
	}

}