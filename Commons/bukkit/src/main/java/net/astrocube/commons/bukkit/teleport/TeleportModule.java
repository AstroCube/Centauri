package net.astrocube.commons.bukkit.teleport;

import me.fixeddev.inject.ProtectedModule;
import net.astrocube.api.bukkit.teleport.CrossTeleportExchanger;
import net.astrocube.api.bukkit.teleport.RangeDiscriminator;
import net.astrocube.api.bukkit.teleport.ServerTeleportDispatcher;
import net.astrocube.api.bukkit.teleport.UserTeleportDispatcher;

public class TeleportModule extends ProtectedModule {

    @Override
    public void configure() {
        bind(RangeDiscriminator.class).to(CoreRangeDiscriminator.class);
        bind(UserTeleportDispatcher.class).to(CoreUserTeleportDispatcher.class);
        bind(ServerTeleportDispatcher.class).to(CoreServerTeleportDispatcher.class);
        bind(CrossTeleportExchanger.class).to(CoreCrossTeleportExchanger.class);
    }

}
