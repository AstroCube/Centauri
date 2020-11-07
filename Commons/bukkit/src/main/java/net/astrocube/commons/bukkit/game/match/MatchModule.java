package net.astrocube.commons.bukkit.game.match;

import me.fixeddev.inject.ProtectedModule;
import net.astrocube.api.bukkit.game.match.MatchAssigner;
import net.astrocube.api.bukkit.game.match.MatchStateUpdater;
import net.astrocube.api.bukkit.game.match.control.MatchJoinAuthorization;
import net.astrocube.api.bukkit.game.match.control.MatchScheduler;
import net.astrocube.api.bukkit.game.matchmaking.AvailableMatchProvider;
import net.astrocube.api.bukkit.game.matchmaking.IdealMatchSelector;
import net.astrocube.api.bukkit.game.matchmaking.MatchmakingRegistryHandler;
import net.astrocube.commons.bukkit.game.match.control.CoreMatchJoinAuthorization;
import net.astrocube.commons.bukkit.game.match.control.CoreMatchScheduler;
import net.astrocube.commons.bukkit.game.matchmaking.CoreAvailableMatchProvider;
import net.astrocube.commons.bukkit.game.matchmaking.CoreIdealMatchSelector;
import net.astrocube.commons.bukkit.game.matchmaking.CoreMatchmakingRegistryHandler;

public class MatchModule extends ProtectedModule {

    @Override
    public void configure() {
        bind(MatchAssigner.class).to(CoreMatchAssigner.class);
        bind(MatchStateUpdater.class).to(CoreMatchStateUpdater.class);
        bind(MatchScheduler.class).to(CoreMatchScheduler.class);
        bind(MatchJoinAuthorization.class).to(CoreMatchJoinAuthorization.class);
    }

}
