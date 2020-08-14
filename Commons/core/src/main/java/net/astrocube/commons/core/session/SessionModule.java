package net.astrocube.commons.core.session;

import net.astrocube.api.core.session.SessionService;
import net.astrocube.commons.core.inject.ProtectedModule;

public class SessionModule extends ProtectedModule {

    @Override
    public void configure() {
        bind(SessionService.class).to(CoreSessionService.class);
    }

}
