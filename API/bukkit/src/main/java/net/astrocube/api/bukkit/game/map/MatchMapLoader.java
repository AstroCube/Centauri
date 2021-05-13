package net.astrocube.api.bukkit.game.map;

import net.astrocube.api.bukkit.virtual.game.match.Match;
import net.astrocube.slime.api.world.SlimeWorld;

public interface MatchMapLoader {

	SlimeWorld loadMatchMap(Match match) throws Exception;

}
