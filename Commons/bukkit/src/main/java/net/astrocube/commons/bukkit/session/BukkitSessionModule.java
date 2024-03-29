package net.astrocube.commons.bukkit.session;

import me.fixeddev.inject.ProtectedModule;
import net.astrocube.api.bukkit.session.InvalidSessionMessageMatcher;
import net.astrocube.api.bukkit.session.SessionCacheInvalidator;
import net.astrocube.api.bukkit.session.SessionValidatorHandler;
import net.astrocube.commons.bukkit.session.validation.CoreInvalidSessionMessageMatcher;
import net.astrocube.commons.bukkit.session.validation.CoreSessionCacheInvalidator;
import net.astrocube.commons.bukkit.session.validation.CoreSessionValidator;

public class BukkitSessionModule extends ProtectedModule {

    @Override
    public void configure() {
        bind(SessionValidatorHandler.class).to(CoreSessionValidator.class);
        bind(SessionCacheInvalidator.class).to(CoreSessionCacheInvalidator.class);
        bind(InvalidSessionMessageMatcher.class).to(CoreInvalidSessionMessageMatcher.class);
    }

}