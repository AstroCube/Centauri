package net.astrocube.commons.bukkit.session;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.astrocube.api.bukkit.session.InvalidSessionMessageMatcher;
import net.astrocube.api.bukkit.session.SessionValidatorHandler;
import net.astrocube.api.core.virtual.session.SessionValidateDoc;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

@Singleton
public class CoreSessionValidator implements SessionValidatorHandler {

    private @Inject InvalidSessionMessageMatcher invalidSessionMessageMatcher;

    @Override
    public void validateSession(AsyncPlayerPreLoginEvent event, SessionValidateDoc.Complete authorization) {
        if (authorization.isMultiAccount()) {
            event.setKickMessage(invalidSessionMessageMatcher.generateSessionMessage(authorization.getUser()));
            event.setLoginResult(AsyncPlayerPreLoginEvent.Result.KICK_OTHER);
            return;
        }

        //TODO: Check if user has punishments


    }

}
