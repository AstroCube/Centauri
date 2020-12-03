package net.astrocube.api.bukkit.game.event;

import lombok.Getter;
import net.astrocube.api.bukkit.game.match.UserMatchJoiner;
import net.astrocube.api.bukkit.virtual.game.match.Match;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

@Getter
public class GameUserDisconnectEvent extends PlayerEvent {

    private final static HandlerList HANDLER_LIST = new HandlerList();
    private final Match match;

    public GameUserDisconnectEvent(Match match, Player player) {
        super(player);
        this.match = match;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLER_LIST;
    }

    public static HandlerList getHandlerList() {
        return HANDLER_LIST;
    }
}
