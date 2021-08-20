package net.astrocube.commons.bukkit.game.match;

import net.astrocube.inject.ProtectedModule;
import net.astrocube.api.bukkit.game.countdown.CountdownAlerter;
import net.astrocube.api.bukkit.game.countdown.CountdownScheduler;
import net.astrocube.api.bukkit.game.lobby.LobbyScoreboardAssigner;
import net.astrocube.api.bukkit.game.lobby.LobbySessionManager;
import net.astrocube.api.bukkit.game.lobby.LobbySessionModifier;
import net.astrocube.api.bukkit.game.match.*;
import net.astrocube.api.bukkit.game.match.control.MatchParticipantsProvider;
import net.astrocube.api.bukkit.game.match.control.MatchScheduler;
import net.astrocube.api.bukkit.game.match.control.TeamBalancer;
import net.astrocube.api.bukkit.game.matchmaking.SingleMatchAssignation;
import net.astrocube.api.core.message.ChannelBinder;
import net.astrocube.commons.bukkit.game.map.GameMapModule;
import net.astrocube.commons.bukkit.game.match.control.CoreMatchParticipantsProvider;
import net.astrocube.commons.bukkit.game.match.control.CoreMatchScheduler;
import net.astrocube.commons.bukkit.game.match.control.CoreTeamBalancer;
import net.astrocube.commons.bukkit.game.match.control.menu.ControlLobbyModule;
import net.astrocube.commons.bukkit.game.match.countdown.CoreCountdownAlerter;
import net.astrocube.commons.bukkit.game.match.countdown.CoreCountdownScheduler;
import net.astrocube.commons.bukkit.game.match.lobby.CoreLobbyScoreboardAssigner;
import net.astrocube.commons.bukkit.game.match.lobby.CoreLobbySessionManager;
import net.astrocube.commons.bukkit.game.match.lobby.CoreLobbySessionModifier;

public class MatchModule extends ProtectedModule implements ChannelBinder {

	@Override
	public void configure() {
		install(new GameMapModule());
		install(new ControlLobbyModule());

		bind(ActualMatchProvider.class).to(CoreActualMatchProvider.class);
		bind(LobbySessionManager.class).to(CoreLobbySessionManager.class);
		bind(LobbyScoreboardAssigner.class).to(CoreLobbyScoreboardAssigner.class);
		bind(LobbySessionModifier.class).to(CoreLobbySessionModifier.class);
		bind(AvailableMatchServerProvider.class).to(CoreAvailableMatchServerProvider.class);
		bind(MatchAvailabilityChecker.class).to(CoreMatchAvailabilityChecker.class);
		bind(MatchService.class).to(CoreMatchService.class);
		bind(TeamBalancer.class).to(CoreTeamBalancer.class);
		bind(MatchMapUpdater.class).to(CoreMatchMapUpdater.class);

		bindChannel(SingleMatchAssignation.class)
			.registerListener(MatchAssignationHandler.class);

		bind(CountdownAlerter.class).to(CoreCountdownAlerter.class);
		bind(CountdownScheduler.class).to(CoreCountdownScheduler.class);

		bind(MatchAssigner.class).to(CoreMatchAssigner.class);
		bind(MatchParticipantsProvider.class).to(CoreMatchParticipantsProvider.class);
		bind(MatchStateUpdater.class).to(CoreMatchStateUpdater.class);
		bind(MatchScheduler.class).to(CoreMatchScheduler.class);
		bind(ActualMatchCache.class).to(CoreActualMatchCache.class);

		bind(UserMatchJoiner.class).to(CoreUserMatchJoiner.class);

		expose(ActualMatchCache.class);
		expose(ActualMatchProvider.class);

	}

}
