package net.astrocube.api.bukkit.game.event.game;

import lombok.Getter;
import net.astrocube.api.bukkit.game.match.UserMatchJoiner;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

@Getter
public class GameUserJoinEvent extends PlayerEvent {

    private final static HandlerList HANDLER_LIST = new HandlerList();
    private final UserMatchJoiner.Origin origin;
    private final String match;

    public GameUserJoinEvent(String match, Player player, UserMatchJoiner.Origin origin) {
        super(player);
        this.match = match;
        this.origin = origin;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLER_LIST;
    }

    public static HandlerList getHandlerList() {
        return HANDLER_LIST;
    }
}
