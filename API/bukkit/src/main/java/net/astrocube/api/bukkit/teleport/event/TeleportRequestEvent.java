package net.astrocube.api.bukkit.teleport.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.astrocube.api.bukkit.teleport.request.TeleportRequest;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

@Getter
@AllArgsConstructor
public class TeleportRequestEvent extends Event {

    private final static HandlerList HANDLER_LIST = new HandlerList();
    private final TeleportRequest request;

    @Override
    public HandlerList getHandlers() {
        return HANDLER_LIST;
    }

    public static HandlerList getHandlerList() {
        return HANDLER_LIST;
    }

}