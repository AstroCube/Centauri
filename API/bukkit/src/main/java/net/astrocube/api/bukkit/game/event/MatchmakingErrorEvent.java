package net.astrocube.api.bukkit.game.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.astrocube.api.bukkit.game.matchmaking.MatchmakingRequest;
import net.astrocube.api.bukkit.game.matchmaking.error.MatchmakingError;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

@Getter
@AllArgsConstructor
public class MatchmakingErrorEvent extends Event {

    private final static HandlerList HANDLER_LIST = new HandlerList();
    private final MatchmakingError matchmakingError;

    @Override
    public HandlerList getHandlers() {
        return HANDLER_LIST;
    }

    public static HandlerList getHandlerList() {
        return HANDLER_LIST;
    }

}