package net.astrocube.commons.bukkit.session;

import net.astrocube.api.core.virtual.session.SessionValidateDoc;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

public class LoginEventSessionUtil {

    public static SessionValidateDoc.Request retrieveRequestFromEvent(AsyncPlayerPreLoginEvent event) {
        return new SessionValidateDoc.Request() {
            @Override
            public String getUser() {
                return event.getName();
            }

            @Override
            public String getAddress() {
                return event.getAddress().getHostAddress();
            }
        };
    }

}
