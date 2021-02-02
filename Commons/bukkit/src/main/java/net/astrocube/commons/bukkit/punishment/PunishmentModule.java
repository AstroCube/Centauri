package net.astrocube.commons.bukkit.punishment;

import me.fixeddev.inject.ProtectedModule;
import net.astrocube.api.bukkit.punishment.PresetPunishmentCache;

public class PunishmentModule extends ProtectedModule {

    @Override
    public void configure() {
        bind(PresetPunishmentCache.class).to(CorePresetPunishmentCache.class);
    }

}
