package net.astrocube.commons.bukkit.punishment;

import me.fixeddev.inject.ProtectedModule;

import net.astrocube.api.bukkit.punishment.PunishmentAnnouncer;
import net.astrocube.api.bukkit.punishment.PunishmentHelper;

public class PunishmentModule extends ProtectedModule {

    @Override
    protected void configure() {
        bind(PunishmentHelper.class).to(CorePunishmentHelper.class);
        bind(PunishmentAnnouncer.class).to(CorePunishmentAnnouncer.class);
        expose(PunishmentHelper.class);
        expose(PunishmentAnnouncer.class);
    }

}