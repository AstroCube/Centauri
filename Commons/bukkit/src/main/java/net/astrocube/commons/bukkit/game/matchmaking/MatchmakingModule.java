package net.astrocube.commons.bukkit.game.matchmaking;

import com.google.inject.name.Names;
import me.fixeddev.inject.ProtectedModule;
import net.astrocube.api.bukkit.game.matchmaking.AvailableMatchProvider;
import net.astrocube.api.bukkit.game.matchmaking.IdealMatchSelector;
import net.astrocube.api.bukkit.game.matchmaking.MatchmakingRegistryHandler;
import net.astrocube.api.bukkit.game.matchmaking.MatchmakingGenerator;
import net.astrocube.api.bukkit.game.matchmaking.error.MatchmakingErrorBroadcaster;
import net.astrocube.commons.bukkit.game.matchmaking.error.CoreMatchmakingErrorBroadcaster;

public class MatchmakingModule extends ProtectedModule {

    @Override
    public void configure() {
        bind(MatchmakingGenerator.class).annotatedWith(Names.named("sandbox")).to(SandboxMatchmakingGenerator.class);
        bind(MatchmakingGenerator.class).annotatedWith(Names.named("core")).to(CoreMatchmakingGenerator.class);
        bind(MatchmakingRegistryHandler.class).to(CoreMatchmakingRegistryHandler.class);
        bind(IdealMatchSelector.class).to(CoreIdealMatchSelector.class);
        bind(MatchmakingErrorBroadcaster.class).to(CoreMatchmakingErrorBroadcaster.class);
        bind(AvailableMatchProvider.class).to(CoreAvailableMatchProvider.class);
    }

}
