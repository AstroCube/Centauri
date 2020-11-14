package net.astrocube.commons.bukkit.game.match;

import me.fixeddev.inject.ProtectedModule;
import net.astrocube.api.bukkit.game.countdown.CountdownAlerter;
import net.astrocube.api.bukkit.game.countdown.CountdownScheduler;
import net.astrocube.api.bukkit.game.lobby.LobbySessionManager;
import net.astrocube.api.bukkit.game.match.MatchAssigner;
import net.astrocube.api.bukkit.game.match.MatchStateUpdater;
import net.astrocube.api.bukkit.game.match.control.MatchScheduler;
import net.astrocube.commons.bukkit.game.match.countdown.CoreCountdownAlerter;
import net.astrocube.commons.bukkit.game.match.control.CoreMatchScheduler;
import net.astrocube.commons.bukkit.game.match.countdown.CoreCountdownScheduler;
import net.astrocube.commons.bukkit.game.match.lobby.CoreLobbySessionManager;

public class MatchModule extends ProtectedModule {

    @Override
    public void configure() {

        bind(LobbySessionManager.class).to(CoreLobbySessionManager.class);

        bind(CountdownAlerter.class).to(CoreCountdownAlerter.class);
        bind(CountdownScheduler.class).to(CoreCountdownScheduler.class);

        bind(MatchAssigner.class).to(CoreMatchAssigner.class);
        bind(MatchStateUpdater.class).to(CoreMatchStateUpdater.class);
        bind(MatchScheduler.class).to(CoreMatchScheduler.class);

    }

}
