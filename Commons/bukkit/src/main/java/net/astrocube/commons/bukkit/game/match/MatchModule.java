package net.astrocube.commons.bukkit.game.match;

import me.fixeddev.inject.ProtectedModule;
import net.astrocube.api.bukkit.game.match.MatchAssigner;
import net.astrocube.api.bukkit.game.match.MatchStateUpdater;
import net.astrocube.api.bukkit.game.match.control.MatchScheduler;
import net.astrocube.commons.bukkit.game.match.control.CoreMatchScheduler;

public class MatchModule extends ProtectedModule {

    @Override
    public void configure() {
        bind(MatchAssigner.class).to(CoreMatchAssigner.class);
        bind(MatchStateUpdater.class).to(CoreMatchStateUpdater.class);
        bind(MatchScheduler.class).to(CoreMatchScheduler.class);
    }

}
