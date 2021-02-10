package net.astrocube.commons.bukkit.punishment;

import me.fixeddev.inject.ProtectedModule;
import net.astrocube.api.bukkit.punishment.PresetPunishmentCache;
import net.astrocube.api.bukkit.punishment.PunishmentKickProcessor;
import net.astrocube.api.bukkit.punishment.lookup.PunishmentIconGenerator;
import net.astrocube.api.bukkit.punishment.lookup.PunishmentLookupMenu;
import net.astrocube.api.core.message.ChannelBinder;
import net.astrocube.api.core.player.ProxyKickRequest;
import net.astrocube.api.core.punishment.PunishmentHandler;
import net.astrocube.api.core.virtual.punishment.Punishment;
import net.astrocube.commons.bukkit.punishment.lookup.CorePunishmentIconGenerator;
import net.astrocube.commons.bukkit.punishment.lookup.CorePunishmentLookupMenu;

public class PunishmentModule extends ProtectedModule implements ChannelBinder {

    @Override
    public void configure() {

        bind(PunishmentLookupMenu.class).to(CorePunishmentLookupMenu.class);
        bind(PunishmentIconGenerator.class).to(CorePunishmentIconGenerator.class);

        bind(PresetPunishmentCache.class).to(CorePresetPunishmentCache.class);
        bind(PunishmentHandler.class).to(CorePunishmentHandler.class);
        bind(PunishmentKickProcessor.class).to(CorePunishmentKickProcessor.class);
        bindChannel(Punishment.class).registerHandler(new PunishmentBroadcastHandler());
        bindChannel(ProxyKickRequest.class);
    }

}
