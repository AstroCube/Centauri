package net.astrocube.commons.bukkit.virtual;

import me.fixeddev.inject.ProtectedModule;
import net.astrocube.commons.core.virtual.*;

public class BukkitVirtualModule extends ProtectedModule {

    @Override
    protected void configure() {
        install(new VirtualModule());
        install(new MatchModule());
        install(new GameMapModelModule());
    }

}
