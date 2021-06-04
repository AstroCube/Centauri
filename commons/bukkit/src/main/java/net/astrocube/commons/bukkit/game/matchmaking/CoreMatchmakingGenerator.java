package net.astrocube.commons.bukkit.game.matchmaking;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.astrocube.api.bukkit.game.matchmaking.MatchAssignable;
import net.astrocube.api.bukkit.game.matchmaking.MatchmakingGenerator;
import net.astrocube.api.bukkit.game.matchmaking.MatchmakingRegistryHandler;
import net.astrocube.api.core.virtual.gamemode.GameMode;
import net.astrocube.api.core.virtual.gamemode.SubGameMode;
import org.bukkit.entity.Player;

import java.util.HashSet;

@Singleton
public class CoreMatchmakingGenerator implements MatchmakingGenerator {

	private @Inject MatchmakingRegistryHandler matchmakingRegistryHandler;

	@Override
	public void pairMatch(Player player, GameMode mode, SubGameMode subMode) throws Exception {

		MatchAssignable assignable = new MatchAssignable(
			player.getDatabaseIdentifier(),
			new HashSet<>() // TODO: Get party
		);

		matchmakingRegistryHandler.generateRequest(
			assignable,
			mode.getId(),
			subMode.getId(),
			"",
			null
		);

	}

}
