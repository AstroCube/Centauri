package net.astrocube.api.bukkit.authentication.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

@Getter
@AllArgsConstructor
public class AuthenticationStartEvent extends Event {

    private final static HandlerList HANDLER_LIST = new HandlerList();
    private final boolean registered;
    private final Player player;
    private final String related;

    @Override
    public HandlerList getHandlers() {
        return HANDLER_LIST;
    }

    public static HandlerList getHandlerList() {
        return HANDLER_LIST;
    }

}