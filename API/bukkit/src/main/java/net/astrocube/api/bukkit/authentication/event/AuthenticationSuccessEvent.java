package net.astrocube.api.bukkit.authentication.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.astrocube.api.bukkit.authentication.AuthenticationGateway;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

@Getter
@AllArgsConstructor
public class AuthenticationSuccessEvent extends Event {

	private final static HandlerList HANDLER_LIST = new HandlerList();
	private final AuthenticationGateway gateway;
	private final Player player;

	@Override
	public HandlerList getHandlers() {
		return HANDLER_LIST;
	}

	public static HandlerList getHandlerList() {
		return HANDLER_LIST;
	}

}