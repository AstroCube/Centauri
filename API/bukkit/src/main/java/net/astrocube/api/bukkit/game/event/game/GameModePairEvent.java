package net.astrocube.api.bukkit.game.event.game;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

@Getter
@AllArgsConstructor
public class GameModePairEvent extends Event {

    private final static HandlerList HANDLER_LIST = new HandlerList();
    private final String gameMode;
    private final String subGameMode;

    @Override
    public HandlerList getHandlers() {
        return HANDLER_LIST;
    }

    public static HandlerList getHandlerList() {
        return HANDLER_LIST;
    }

}