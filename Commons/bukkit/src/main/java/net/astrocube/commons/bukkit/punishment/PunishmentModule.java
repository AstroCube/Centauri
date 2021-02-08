package net.astrocube.commons.bukkit.punishment;

import me.fixeddev.inject.ProtectedModule;
import net.astrocube.api.bukkit.punishment.PresetPunishmentCache;
import net.astrocube.api.core.message.ChannelBinder;
import net.astrocube.api.core.punishment.PunishmentHandler;
import net.astrocube.api.core.virtual.punishment.Punishment;

public class PunishmentModule extends ProtectedModule implements ChannelBinder {

    @Override
    public void configure() {
        bind(PresetPunishmentCache.class).to(CorePresetPunishmentCache.class);
        bind(PunishmentHandler.class).to(CorePunishmentHandler.class);
        bindChannel(Punishment.class).registerHandler(new PunishmentBroadcastHandler());
    }

}
