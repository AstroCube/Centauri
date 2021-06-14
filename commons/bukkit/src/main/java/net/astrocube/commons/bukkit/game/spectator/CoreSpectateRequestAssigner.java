package net.astrocube.commons.bukkit.game.spectator;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.astrocube.api.bukkit.game.event.spectator.SpectateRequestEvent;
import net.astrocube.api.bukkit.game.match.ActualMatchCache;
import net.astrocube.api.bukkit.game.match.MatchAvailabilityChecker;
import net.astrocube.api.bukkit.game.spectator.SpectateRequest;
import net.astrocube.api.bukkit.game.spectator.SpectateRequestAssigner;
import net.astrocube.api.bukkit.virtual.game.match.Match;
import net.astrocube.api.core.service.find.FindService;
import net.astrocube.api.core.service.query.QueryService;
import net.astrocube.api.core.virtual.server.Server;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

@Singleton
public class CoreSpectateRequestAssigner implements SpectateRequestAssigner {

	private @Inject QueryService<Match> queryService;
	private @Inject FindService<Server> findService;
	private @Inject ActualMatchCache actualMatchCache;
	private @Inject MatchAvailabilityChecker matchAvailabilityChecker;
	private @Inject ObjectMapper mapper;

	@Override
	public void assignRequest(String gameMode, @Nullable String subMode, String requester) {

		Player player = Bukkit.getPlayerByIdentifier(requester);

		if (player != null) {
			ObjectNode node = mapper.createObjectNode();

			node.put("gamemode", gameMode);
			node.put("subGamemode", subMode);

			queryService.query(node).callback(matchResponse -> {

				if (!matchResponse.isSuccessful()) {
					Bukkit.getPluginManager().callEvent(new SpectateRequestEvent(player, null, SpectateRequest.State.ERROR));
					return;
				}

				matchResponse.ifSuccessful(response -> {

					Optional<Match> match = response.getFoundModels().stream().findAny();

					if (!match.isPresent()) {
						Bukkit.getPluginManager().callEvent(new SpectateRequestEvent(player, null, SpectateRequest.State.VOIDED));
						return;
					}

					match.ifPresent(foundMatch -> successAssignSpectate(foundMatch, requester, foundMatch.getId()));

				});

			});
		}

	}

	@Override
	public void assignRequestToPlayer(String match, String requester, String target) {

		Player player = Bukkit.getPlayerByIdentifier(requester);

		if (player != null) {
			try {

				Optional<Match> optionalMatch = actualMatchCache.get(target);

				if (!optionalMatch.isPresent()) {
					Bukkit.getPluginManager().callEvent(new SpectateRequestEvent(player, null, SpectateRequest.State.VOIDED));
					return;
				}

				optionalMatch.ifPresent(foundMatch -> successAssignSpectate(foundMatch, requester, foundMatch.getServer()));

			} catch (Exception e) {
				Bukkit.getPluginManager().callEvent(new SpectateRequestEvent(player, null, SpectateRequest.State.ERROR));
			}
		}

	}

	private void successAssignSpectate(Match match, String requester, String serverId) {

		Player player = Bukkit.getPlayerByIdentifier(requester);

		if (player != null) {

			findService.find(serverId).callback(serverCallback -> {

				if (!serverCallback.isSuccessful()) {
					Bukkit.getPluginManager().callEvent(new SpectateRequestEvent(player, null, SpectateRequest.State.ERROR));
					return;
				}

				serverCallback.ifSuccessful(server ->
					Bukkit.getPluginManager().callEvent(
						new SpectateRequestEvent(
							player,
							new SpectateRequest(
									requester,
									match,
									server.getSlug()
							),
							SpectateRequest.State.SUCCESS
						)
					)
				);

			});

		}

	}

}
