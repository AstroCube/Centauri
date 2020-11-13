package net.astrocube.commons.bukkit.virtual;

import me.fixeddev.inject.ProtectedModule;
import net.astrocube.commons.core.virtual.ModelModule;

public class BukkitModelModule extends ProtectedModule {

    @Override
    protected void configure() {
        install(new ModelModule());
        install(new MatchModule());
        install(new GameMapModelModule());
        install(new GoalModelModule());
    }
}