package net.astrocube.commons.bukkit.session;

import net.astrocube.api.bukkit.authentication.event.SessionSwitchBroadcast;
import net.astrocube.api.core.message.MessageHandler;
import net.astrocube.api.core.message.Metadata;
import net.astrocube.api.core.session.SessionSwitchWrapper;
import org.bukkit.Bukkit;

public class AuthenticationSuccessHandler implements MessageHandler<SessionSwitchWrapper> {

    @Override
    public Class<SessionSwitchWrapper> type() {
        return SessionSwitchWrapper.class;
    }

    @Override
    public void handleDelivery(SessionSwitchWrapper message, Metadata properties) {
        Bukkit.getPluginManager().callEvent(new SessionSwitchBroadcast(message));
    }

}
