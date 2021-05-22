package net.astrocube.commons.bukkit.game.match;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.reflect.TypeToken;
import com.google.inject.Inject;
import net.astrocube.api.bukkit.game.match.MatchService;
import net.astrocube.api.bukkit.game.match.request.*;
import net.astrocube.api.bukkit.game.matchmaking.MatchAssignable;
import net.astrocube.api.bukkit.virtual.game.match.Match;
import net.astrocube.api.bukkit.virtual.game.match.MatchDoc;
import net.astrocube.api.core.http.HttpClient;
import net.astrocube.api.core.http.RequestOptions;
import net.astrocube.api.core.message.Channel;
import net.astrocube.api.core.message.Messenger;
import net.astrocube.api.core.model.ModelMeta;
import net.astrocube.commons.core.http.CoreRequestCallable;

import java.util.HashMap;
import java.util.Set;

public class CoreMatchService implements MatchService {

	private @Inject ObjectMapper objectMapper;
	private @Inject HttpClient httpClient;
	private @Inject ModelMeta<Match, MatchDoc.Partial> modelMeta;

	private final Messenger messenger;

	@Inject
	public CoreMatchService(Messenger messenger) {
		this.messenger = messenger;
	}

	@Override
	public void assignSpectator(String user, String match, boolean join) throws Exception {
		Channel<SpectatorAssignMessage> spectatorAssignMessageChannel = messenger.getChannel(SpectatorAssignMessage.class);
		spectatorAssignMessageChannel.sendMessage(new SpectatorAssignMessage() {
			@Override
			public String getUser() {
				return user;
			}

			@Override
			public boolean isJoin() {
				return join;
			}

			@Override
			public String getMatch() {
				return match;
			}
		}, new HashMap<>());
	}

	@Override
	public void assignTeams(Set<MatchDoc.Team> teams, String match) throws Exception {
		Channel<TeamAssignMessage> teamAssignMessageChannel = messenger.getChannel(TeamAssignMessage.class);
		teamAssignMessageChannel.sendMessage(new TeamAssignMessage() {
			@Override
			public Set<MatchDoc.Team> getTeams() {
				return teams;
			}

			@Override
			public String getMatch() {
				return match;
			}
		}, new HashMap<>());
	}

	@Override
	public void unAssignPending(String user, String match) throws Exception {
		Channel<PendingUnAssignMessage> pendingAssignMessageChannel = messenger.getChannel(PendingUnAssignMessage.class);
		pendingAssignMessageChannel.sendMessage(new PendingUnAssignMessage() {
			@Override
			public String getUser() {
				return user;
			}

			@Override
			public String getMatch() {
				return match;
			}
		}, new HashMap<>());
	}

	@Override
	public void assignPending(MatchAssignable pendingRequest, String match) throws Exception {
		Channel<MatchmakingAssignMessage> matchmakingAssignMessageChannel = messenger.getChannel(MatchmakingAssignMessage.class);
		matchmakingAssignMessageChannel.sendMessage(new MatchmakingAssignMessage() {
			@Override
			public MatchAssignable getAssignable() {
				return pendingRequest;
			}

			@Override
			public String getMatch() {
				return match;
			}
		}, new HashMap<>());
	}

	@Override
	public void matchCleanup() throws Exception {
		httpClient.executeRequestSync(
			this.modelMeta.getRouteKey() + "/cleanup",
			new CoreRequestCallable<>(TypeToken.of(Void.class), objectMapper),
			new RequestOptions(
				RequestOptions.Type.POST,
				new HashMap<>(),
				"",
				null
			)
		);
	}

	@Override
	public void assignVictory(String match, Set<String> winners) throws Exception {
		Channel<VictoryAssignMessage> victoryAssignMessageChannel = messenger.getChannel(VictoryAssignMessage.class);
		victoryAssignMessageChannel.sendMessage(new VictoryAssignMessage() {
			@Override
			public Set<String> getWinners() {
				return winners;
			}

			@Override
			public String getMatch() {
				return match;
			}
		}, new HashMap<>());
	}

	@Override
	public void disqualify(String match, String user) throws Exception {
		Channel<MatchDisqualifyMessage> matchDisqualifyMessageChannel = messenger.getChannel(MatchDisqualifyMessage.class);
		matchDisqualifyMessageChannel.sendMessage(new MatchDisqualifyMessage() {
			@Override
			public String getUser() {
				return user;
			}

			@Override
			public String getMatch() {
				return match;
			}
		}, new HashMap<>());
	}

	@Override
	public void privatizeMatch(String requester, String match) throws Exception {
		Channel<MatchPrivatizeMessage> matchPrivatizeMessageChannel = messenger.getChannel(MatchPrivatizeMessage.class);
		matchPrivatizeMessageChannel.sendMessage(new MatchPrivatizeMessage() {
			@Override
			public String getRequester() {
				return requester;
			}

			@Override
			public String getMatch() {
				return match;
			}
		}, new HashMap<>());
	}

}
