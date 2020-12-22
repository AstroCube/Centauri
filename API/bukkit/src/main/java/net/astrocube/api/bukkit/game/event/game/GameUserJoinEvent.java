package net.astrocube.api.bukkit.game.event.game;

import lombok.Getter;
import net.astrocube.api.bukkit.game.match.UserMatchJoiner;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

@Getter
public class GameUserJoinEvent extends PlayerEvent {

    private final static HandlerList HANDLER_LIST = new HandlerList();
    private final UserMatchJoiner.Status status;
    private final String match;

    public GameUserJoinEvent(String match, Player player, UserMatchJoiner.Status status) {
        super(player);
        this.match = match;
        this.status = status;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLER_LIST;
    }

    public static HandlerList getHandlerList() {
        return HANDLER_LIST;
    }
}
