package net.astrocube.commons.core.session;

import me.fixeddev.inject.ProtectedModule;
import net.astrocube.api.core.session.SessionService;

public class SessionModule extends ProtectedModule {

    @Override
    public void configure() {
        bind(SessionService.class).to(CoreSessionService.class);
    }

}
