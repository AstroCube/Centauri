package net.astrocube.api.bukkit.game.event.spectator;

import lombok.Getter;
import net.astrocube.api.bukkit.game.spectator.SpectateRequest;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

import javax.annotation.Nullable;

@Getter
public class SpectateRequestEvent extends PlayerEvent {

    private final static HandlerList HANDLER_LIST = new HandlerList();
    private final @Nullable SpectateRequest spectateRequest;
    private final SpectateRequest.State state;


    public SpectateRequestEvent(Player who, @Nullable SpectateRequest spectateRequest, SpectateRequest.State state) {
        super(who);
        this.spectateRequest = spectateRequest;
        this.state = state;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLER_LIST;
    }

    public static HandlerList getHandlerList() {
        return HANDLER_LIST;
    }

}