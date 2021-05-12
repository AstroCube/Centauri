package net.astrocube.commons.bukkit.game.match.control;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.astrocube.api.bukkit.game.channel.MatchChannelProvider;
import net.astrocube.api.bukkit.game.event.match.MatchScheduleEvent;
import net.astrocube.api.bukkit.game.exception.GameControlException;
import net.astrocube.api.bukkit.game.match.MatchAssigner;
import net.astrocube.api.bukkit.game.match.control.MatchScheduler;
import net.astrocube.api.bukkit.game.matchmaking.MatchmakingRequest;
import net.astrocube.api.bukkit.virtual.game.match.Match;
import net.astrocube.api.bukkit.virtual.game.match.MatchDoc;
import net.astrocube.api.core.server.ServerService;
import net.astrocube.api.core.service.create.CreateService;
import net.astrocube.api.core.virtual.server.Server;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import javax.annotation.Nullable;

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


		MatchDoc.Partial match = new MatchDoc.Identity() {

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
