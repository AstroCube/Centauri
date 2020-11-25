package net.astrocube.commons.bukkit.game.matchmaking;

import me.fixeddev.inject.ProtectedModule;
import net.astrocube.api.bukkit.game.matchmaking.AvailableMatchProvider;
import net.astrocube.api.bukkit.game.matchmaking.IdealMatchSelector;
import net.astrocube.api.bukkit.game.matchmaking.MatchmakingRegistryHandler;
import net.astrocube.api.bukkit.game.matchmaking.MatchmakingSandboxProvider;

public class MatchmakingModule extends ProtectedModule {

    @Override
    public void configure() {
        bind(MatchmakingSandboxProvider.class).to(CoreMatchmakingSandboxProvider.class);
        bind(MatchmakingRegistryHandler.class).to(CoreMatchmakingRegistryHandler.class);
        bind(IdealMatchSelector.class).to(CoreIdealMatchSelector.class);
        bind(AvailableMatchProvider.class).to(CoreAvailableMatchProvider.class);
    }

}
