package net.astrocube.commons.bukkit.game;

import me.fixeddev.inject.ProtectedModule;
import net.astrocube.api.bukkit.game.GameControlPair;
import net.astrocube.api.bukkit.game.match.control.PendingMatchFinder;
import net.astrocube.api.bukkit.game.matchmaking.SingleMatchAssignation;
import net.astrocube.api.bukkit.game.matchmaking.error.MatchmakingError;
import net.astrocube.api.core.message.ChannelBinder;
import net.astrocube.commons.bukkit.game.map.GameMapModule;
import net.astrocube.commons.bukkit.game.match.MatchAssignationHandler;
import net.astrocube.commons.bukkit.game.match.MatchModule;
import net.astrocube.commons.bukkit.game.match.control.CorePendingMatchFinder;
import net.astrocube.commons.bukkit.game.matchmaking.MatchmakingModule;
import net.astrocube.commons.bukkit.game.matchmaking.error.MatchmakingErrorHandler;

public class GameModule extends ProtectedModule implements ChannelBinder {

    @Override
    public void configure() {
        install(new MatchmakingModule());
        install(new MatchModule());
        bindChannel(MatchmakingError.class).registerHandler(new MatchmakingErrorHandler());
        bind(GameControlPair.class).to(CoreGameControlPair.class);
        bind(PendingMatchFinder.class).to(CorePendingMatchFinder.class);
    }

}
