package net.astrocube.commons.bukkit.session;

import me.fixeddev.inject.ProtectedModule;
import net.astrocube.api.bukkit.session.SessionValidatorHandler;

public class BukkitSessionModule extends ProtectedModule {

    @Override
    public void configure() {
        bind(SessionValidatorHandler.class).to(CoreSessionValidator.class);
    }

}