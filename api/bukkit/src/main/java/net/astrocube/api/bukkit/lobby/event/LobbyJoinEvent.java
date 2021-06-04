package net.astrocube.api.bukkit.lobby.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.astrocube.api.core.virtual.user.User;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

@Getter
@AllArgsConstructor
public class LobbyJoinEvent extends Event {

	private final static HandlerList HANDLER_LIST = new HandlerList();
	private final Player player;
	private final User user;

	@Override
	public HandlerList getHandlers() {
		return HANDLER_LIST;
	}

	public static HandlerList getHandlerList() {
		return HANDLER_LIST;
	}

}