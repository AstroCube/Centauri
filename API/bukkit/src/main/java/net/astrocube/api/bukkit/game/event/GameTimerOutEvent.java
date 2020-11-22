package net.astrocube.api.bukkit.game.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.astrocube.api.bukkit.virtual.game.match.Match;
import net.astrocube.api.core.virtual.gamemode.GameMode;
import net.astrocube.api.core.virtual.gamemode.SubGameMode;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

@Getter
@AllArgsConstructor
public class GameTimerOutEvent extends Event {

    private final static HandlerList HANDLER_LIST = new HandlerList();
    private final String match;

    @Override
    public HandlerList getHandlers() {
        return HANDLER_LIST;
    }

    public static HandlerList getHandlerList() {
        return HANDLER_LIST;
    }

}