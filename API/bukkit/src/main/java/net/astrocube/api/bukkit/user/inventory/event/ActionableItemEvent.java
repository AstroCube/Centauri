package net.astrocube.api.bukkit.user.inventory.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.astrocube.api.core.virtual.user.User;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.block.Action;

@Getter
@AllArgsConstructor
public class ActionableItemEvent extends Event {

	private final static HandlerList HANDLER_LIST = new HandlerList();
	private final Player player;
	private final User user;
	private final String action;
	private final Action click;

	@Override
	public HandlerList getHandlers() {
		return HANDLER_LIST;
	}

	public static HandlerList getHandlerList() {
		return HANDLER_LIST;
	}

}