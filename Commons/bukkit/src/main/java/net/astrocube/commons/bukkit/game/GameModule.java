package net.astrocube.commons.bukkit.game;

import me.fixeddev.inject.ProtectedModule;
import net.astrocube.api.bukkit.game.GameControlPair;
import net.astrocube.api.bukkit.game.match.control.PendingMatchFinder;
import net.astrocube.api.bukkit.game.matchmaking.MatchmakingGenerator;
import net.astrocube.api.bukkit.game.matchmaking.error.MatchmakingError;
import net.astrocube.api.bukkit.game.scheduler.RunningMatchBalancer;
import net.astrocube.api.core.message.ChannelBinder;
import net.astrocube.commons.bukkit.game.channel.GameChannelModule;
import net.astrocube.commons.bukkit.game.match.MatchModule;
import net.astrocube.commons.bukkit.game.match.control.CorePendingMatchFinder;
import net.astrocube.commons.bukkit.game.matchmaking.MatchmakingModule;
import net.astrocube.commons.bukkit.game.matchmaking.error.MatchmakingErrorHandler;
import net.astrocube.commons.bukkit.game.scheduler.CoreRunningMatchBalancer;
import net.astrocube.commons.bukkit.game.spectator.SpectatorModule;

public class GameModule extends ProtectedModule implements ChannelBinder {

    @Override
    public void configure() {
        install(new MatchmakingModule());
        install(new SpectatorModule());
        install(new MatchModule());
        install(new GameChannelModule());
        bindChannel(MatchmakingError.class).registerHandler(new MatchmakingErrorHandler());
        bind(GameControlPair.class).to(CoreGameControlPair.class);
        bind(PendingMatchFinder.class).to(CorePendingMatchFinder.class);
        bind(RunningMatchBalancer.class).to(CoreRunningMatchBalancer.class);
    }

}
