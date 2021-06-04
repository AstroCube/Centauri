package net.astrocube.api.bukkit.game.event.spectator;

import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

@Getter
public class SpectatorAssignEvent extends PlayerEvent {

	private final static HandlerList HANDLER_LIST = new HandlerList();
	private final String match;

	public SpectatorAssignEvent(Player player, String match) {
		super(player);
		this.match = match;
	}


	@Override
	public HandlerList getHandlers() {
		return HANDLER_LIST;
	}

	public static HandlerList getHandlerList() {
		return HANDLER_LIST;
	}

}