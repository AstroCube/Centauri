package net.astrocube.commons.bukkit.game;

import me.fixeddev.inject.ProtectedModule;
import net.astrocube.api.bukkit.game.GameControlPair;
import net.astrocube.api.bukkit.game.match.control.PendingMatchFinder;
import net.astrocube.commons.bukkit.game.match.MatchModule;
import net.astrocube.commons.bukkit.game.match.control.CorePendingMatchFinder;
import net.astrocube.commons.bukkit.game.matchmaking.MatchmakingModule;

public class GameModule extends ProtectedModule {

    @Override
    public void configure() {
        install(new MatchmakingModule());
        install(new MatchModule());
        bind(GameControlPair.class).to(CoreGameControlPair.class);
        bind(PendingMatchFinder.class).to(CorePendingMatchFinder.class);
    }

}
