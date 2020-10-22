package net.astrocube.api.bukkit.game.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.astrocube.api.core.virtual.gamemode.GameMode;
import net.astrocube.api.core.virtual.gamemode.SubGameMode;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import net.astrocube.api.bukkit.game.matchmaking.MatchmakingRequest;

@Getter
@AllArgsConstructor
/**
 * This will invoke a sanitize request in order to supply the
 * {@link MatchmakingRequest}s.
 */
public class MatchControlSanitizeEvent extends Event {

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
