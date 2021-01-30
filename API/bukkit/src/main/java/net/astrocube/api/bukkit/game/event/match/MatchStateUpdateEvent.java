package net.astrocube.api.bukkit.game.event.match;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.astrocube.api.bukkit.virtual.game.match.MatchDoc;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

@Getter
@AllArgsConstructor
public class MatchStateUpdateEvent extends Event {

    private final static HandlerList HANDLER_LIST = new HandlerList();
    private final String match;
    private final MatchDoc.Status status;

    @Override
    public HandlerList getHandlers() {
        return HANDLER_LIST;
    }

    public static HandlerList getHandlerList() {
        return HANDLER_LIST;
    }

}