package net.astrocube.commons.bukkit.game.map;

import me.fixeddev.inject.ProtectedModule;
import net.astrocube.api.bukkit.game.map.GameMapCache;
import net.astrocube.api.bukkit.game.map.GameMapProvider;
import net.astrocube.api.bukkit.game.map.GameMapService;
import net.astrocube.api.bukkit.game.map.MatchMapLoader;

public class GameMapModule extends ProtectedModule {

    @Override
    public void configure() {
        bind(GameMapCache.class).to(CoreGameMapCache.class);
        bind(GameMapProvider.class).to(CoreGameMapProvider.class);
        bind(GameMapService.class).to(CoreGameMapService.class);
        bind(MatchMapLoader.class).to(CoreMatchMapLoader.class);
    }

}
