package net.astrocube.commons.core.punishment;

import me.fixeddev.inject.ProtectedModule;
import net.astrocube.api.core.punishment.PunishmentHandler;

public class PunishmentModule extends ProtectedModule {

    @Override
    protected void configure() {
        bind(PunishmentHandler.class).to(CorePunishmentHandler.class);
        expose(PunishmentHandler.class);
    }

}