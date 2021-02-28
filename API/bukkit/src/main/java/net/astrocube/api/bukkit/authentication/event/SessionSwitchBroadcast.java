package net.astrocube.api.bukkit.authentication.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.astrocube.api.bukkit.authentication.AuthenticationGateway;
import net.astrocube.api.core.session.SessionSwitchWrapper;
import net.astrocube.api.core.virtual.user.User;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

@Getter
@AllArgsConstructor
public class SessionSwitchBroadcast extends Event {

    private final static HandlerList HANDLER_LIST = new HandlerList();
    private final SessionSwitchWrapper session;

    @Override
    public HandlerList getHandlers() {
        return HANDLER_LIST;
    }

    public static HandlerList getHandlerList() {
        return HANDLER_LIST;
    }

}