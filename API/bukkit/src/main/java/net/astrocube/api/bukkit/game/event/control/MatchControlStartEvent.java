package net.astrocube.api.bukkit.game.event.control;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.astrocube.api.core.virtual.gamemode.GameMode;
import net.astrocube.api.core.virtual.gamemode.SubGameMode;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

@Getter
@AllArgsConstructor
public class MatchControlStartEvent extends Event {

    private final static HandlerList HANDLER_LIST = new HandlerList();
    private final GameMode gameMode;
    private final SubGameMode subGameMode;

    @Override
    public HandlerList getHandlers() {
        return HANDLER_LIST;
    }

    public static HandlerList getHandlerList() {
        return HANDLER_LIST;
    }

}
