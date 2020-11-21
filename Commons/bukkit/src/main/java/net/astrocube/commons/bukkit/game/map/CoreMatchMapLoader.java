package net.astrocube.commons.bukkit.game.map;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.astrocube.api.bukkit.game.map.GameMapProvider;
import net.astrocube.api.bukkit.game.map.MatchMapLoader;
import net.astrocube.api.bukkit.virtual.game.map.GameMap;
import net.astrocube.api.bukkit.virtual.game.match.Match;
import net.astrocube.api.core.service.find.FindService;
import net.astrocube.slime.api.loaders.SlimeLoader;
import net.astrocube.slime.api.world.SlimeWorld;
import net.astrocube.slime.api.world.properties.SlimePropertyMap;
import net.astrocube.slime.core.loaders.LoaderUtils;

@Singleton
public class CoreMatchMapLoader implements MatchMapLoader {

    private @Inject GameMapProvider gameMapProvider;
    private @Inject FindService<GameMap> findService;
    private final SlimeLoader dummyLoader = new MapDummyLoader();

    @Override
    public SlimeWorld loadMatchMap(Match match) throws Exception {
        GameMap map = findService.findSync(match.getMap());
        GameMapProvider.MapFiles files = gameMapProvider.loadGameMap(map);
        return LoaderUtils.deserializeWorld(dummyLoader, "match_" + match.getId(),
                files.getMapFile(), new SlimePropertyMap(), true);
    }

}
