package net.astrocube.commons.bukkit.game.match;

import me.fixeddev.inject.ProtectedModule;
import net.astrocube.api.bukkit.game.match.MatchAssigner;
import net.astrocube.api.bukkit.game.match.MatchStateUpdater;
import net.astrocube.api.bukkit.game.matchmaking.AvailableMatchProvider;
import net.astrocube.api.bukkit.game.matchmaking.IdealMatchSelector;
import net.astrocube.api.bukkit.game.matchmaking.MatchmakingRegistryHandler;
import net.astrocube.commons.bukkit.game.matchmaking.CoreAvailableMatchProvider;
import net.astrocube.commons.bukkit.game.matchmaking.CoreIdealMatchSelector;
import net.astrocube.commons.bukkit.game.matchmaking.CoreMatchmakingRegistryHandler;

public class MatchModule extends ProtectedModule {

    @Override
    public void configure() {
        bind(MatchAssigner.class).to(CoreMatchAssigner.class);
        bind(MatchStateUpdater.class).to(CoreMatchStateUpdater.class);
    }

}
