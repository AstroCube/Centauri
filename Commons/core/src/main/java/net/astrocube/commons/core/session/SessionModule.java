package net.astrocube.commons.core.session;

import me.fixeddev.inject.ProtectedModule;
import net.astrocube.api.core.session.MojangValidate;
import net.astrocube.api.core.session.SessionAliveInterceptor;
import net.astrocube.api.core.session.SessionService;
import net.astrocube.api.core.session.registry.SessionRegistryManager;

public class SessionModule extends ProtectedModule {

    @Override
    public void configure() {
        bind(SessionRegistryManager.class).to(CoreSessionRegistryManager.class);
        bind(SessionAliveInterceptor.class).to(CoreSessionAliveInterceptor.class);
        bind(MojangValidate.class).to(CoreMojangValidate.class);
        bind(SessionService.class).to(CoreSessionService.class);
    }

}
