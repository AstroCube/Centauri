package net.astrocube.api.bukkit.game.map;

import net.astrocube.api.bukkit.game.exception.GameControlException;
import net.astrocube.api.bukkit.virtual.game.map.GameMap;
import net.astrocube.slime.api.world.SlimeWorld;

import java.io.IOException;
import java.util.Set;

public interface GameMapProvider {

    SlimeWorld loadGameMap(GameMap gameMap) throws IOException, GameControlException;

}