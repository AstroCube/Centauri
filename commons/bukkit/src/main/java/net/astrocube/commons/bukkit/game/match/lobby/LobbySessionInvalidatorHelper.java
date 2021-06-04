package net.astrocube.commons.bukkit.game.match.lobby;

import net.astrocube.api.bukkit.virtual.game.match.Match;
import net.astrocube.api.core.concurrent.Response;
import net.astrocube.api.core.virtual.gamemode.GameMode;
import net.astrocube.api.core.virtual.gamemode.SubGameMode;

import java.util.Optional;

public class LobbySessionInvalidatorHelper {

	public static Optional<SubGameMode> retrieveSubMode(Response<GameMode> gameMode, Match match) {

		if (!gameMode.isSuccessful() || !gameMode.getResponse().isPresent()) {
			return Optional.empty();
		}

		return gameMode.getResponse().get()
			.getSubTypes()
			.stream()
			.filter(sub -> sub.getId().equalsIgnoreCase(match.getSubMode()))
			.findFirst();
	}
}
