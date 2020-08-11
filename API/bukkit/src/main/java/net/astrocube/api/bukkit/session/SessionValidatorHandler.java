package net.astrocube.api.bukkit.session;

import net.astrocube.api.core.virtual.session.SessionValidateDoc;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

public interface SessionValidatorHandler {

    /**
     * Compare and allow/disallow event connection depending on backend response
     * @param event to be compared
     * @param authorization to be provided
     */
    void validateSession(AsyncPlayerPreLoginEvent event, SessionValidateDoc.Complete authorization);

}
