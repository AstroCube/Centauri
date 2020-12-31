package net.astrocube.api.bukkit.game.event.game;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.astrocube.api.bukkit.virtual.game.match.MatchDoc;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.Set;

@Getter
@AllArgsConstructor
public class GameReadyEvent extends Event {

    private final static HandlerList HANDLER_LIST = new HandlerList();
    private final String match;
    private final String configuration;
    private final Set<MatchDoc.Team> teams;

    @Override
    public HandlerList getHandlers() {
        return HANDLER_LIST;
    }

    public static HandlerList getHandlerList() {
        return HANDLER_LIST;
    }

}