package net.astrocube.api.bukkit.game.matchmaking;

import net.astrocube.api.core.virtual.gamemode.GameMode;
import net.astrocube.api.core.virtual.gamemode.SubGameMode;
import org.bukkit.entity.Player;

public interface MatchmakingGenerator {

	default void pairMatch(Player player) throws Exception {
		throw new UnsupportedOperationException("Can not generate match with this generator");
	}

	default void pairMatch(Player player, GameMode mode, SubGameMode subMode) throws Exception {
		throw new UnsupportedOperationException("Can not generate match with this generator");
	}

}
