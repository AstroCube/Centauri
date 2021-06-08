package net.astrocube.commons.bukkit.game.match.control;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.astrocube.api.bukkit.game.channel.MatchChannelProvider;
import net.astrocube.api.bukkit.game.event.match.MatchScheduleEvent;
import net.astrocube.api.bukkit.game.exception.GameControlException;
import net.astrocube.api.bukkit.game.match.MatchAssigner;
import net.astrocube.api.bukkit.game.match.control.MatchScheduler;
import net.astrocube.api.bukkit.game.matchmaking.MatchAssignable;
import net.astrocube.api.bukkit.game.matchmaking.MatchmakingRequest;
import net.astrocube.api.bukkit.virtual.game.match.Match;
import net.astrocube.api.bukkit.virtual.game.match.MatchDoc;
import net.astrocube.api.core.server.ServerService;
import net.astrocube.api.core.service.create.CreateService;
import net.astrocube.api.core.virtual.server.Server;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import javax.annotation.Nullable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Singleton
public class CoreMatchScheduler implements MatchScheduler {

	private @Inject ServerService serverService;
	private @Inject Plugin plugin;
	private @Inject MatchAssigner matchAssigner;
	private @Inject MatchChannelProvider matchChannelProvider;
	private @Inject CreateService<Match, MatchDoc.Partial> createService;

	@Override
	public void schedule(@Nullable MatchmakingRequest request) throws Exception {

		Server actual = serverService.getActual();

		if (request != null) {
			if (
				!request.getGameMode().equalsIgnoreCase(actual.getGameMode()) ||
					!request.getSubGameMode().equalsIgnoreCase(actual.getSubGameMode())
			) {
				throw new GameControlException("Illegal matchmaking request scheduling");
			}
		}


		MatchDoc.Complete match = new MatchDoc.Complete() {

			@Override
			public String getId() {
				return UUID.randomUUID().toString();
			}

			@Override
			public String getMap() {
				return request == null ? "" :
					request.getMap().isPresent() ? request.getMap().get() : "";
			}

			@Override
			public void setMap(String map) {
			}

			@Override
			public String getGameMode() {
				return actual.getGameMode();
			}

			@Override
			public String getSubMode() {
				return actual.getSubGameMode();
			}

			@Override
			public String getServer() {
				try {
					return serverService.getActual().getId();
				} catch (Exception e) {
					throw new IllegalStateException("Cannot get server id", e);
				}
			}

			@Override
			public Set<MatchDoc.Team> getTeams() {
				return new HashSet<>();
			}

			@Override
			public void setTeams(Set<MatchDoc.Team> teams) {
			}

			@Override
			public Set<String> getWinner() {
				return new HashSet<>();
			}

			@Override
			public void setWinner(Set<String> winner) {
			}

			@Override
			public Set<String> getSpectators() {
				return new HashSet<>();
			}

			@Override
			public Set<MatchAssignable> getPending() {
				return new HashSet<>();
			}

			@Override
			public Optional<ObjectNode> getQuery() {
				return Optional.empty();
			}

			@Override
			public Optional<String> getRequestedBy() {
				return Optional.empty();
			}

			@Override
			public boolean isPrivate() {
				return false;
			}

			@Override
			public void setPrivate(boolean privatize) {
			}

			@Override
			public String getPrivatizedBy() {
				return null;
			}

			@Override
			public void setPrivatizedBy(@Nullable String user) {
			}

			@Override
			public MatchDoc.Status getStatus() {
				return MatchDoc.Status.LOBBY;
			}

			@Override
			public void setStatus(MatchDoc.Status status) { }

			@Override
			public LocalDateTime getCreatedAt() {
				return LocalDateTime.now();
			}

			@Override
			public LocalDateTime getUpdatedAt() {
				return LocalDateTime.now();
			}
		};

		Match created = createService.createSync(match);
		matchChannelProvider.createChannel(created.getId());

		plugin.getLogger().info("Scheduled new match with ID " + created.getId());

		Bukkit.getPluginManager().callEvent(new MatchScheduleEvent(created));

		if (request != null) {
			matchAssigner.assign(request.getRequesters(), created);
		}

	}

	@Override
	public void schedule() throws Exception {
		this.schedule(null);
	}
}
