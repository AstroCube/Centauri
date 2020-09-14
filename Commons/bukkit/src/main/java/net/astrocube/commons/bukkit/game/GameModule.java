package net.astrocube.commons.bukkit.game;

import me.fixeddev.inject.ProtectedModule;
import net.astrocube.api.bukkit.game.GameControlPair;

public class GameModule extends ProtectedModule {

    @Override
    public void configure() {
        bind(GameControlPair.class).to(CoreGameControlPair.class);
    }

}
