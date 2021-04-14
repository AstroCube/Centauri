package net.astrocube.commons.bukkit.game.match;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.inject.Inject;
import net.astrocube.api.bukkit.game.match.MatchService;
import net.astrocube.api.bukkit.game.match.request.*;
import net.astrocube.api.bukkit.game.matchmaking.MatchAssignable;
import net.astrocube.api.bukkit.virtual.game.match.Match;
import net.astrocube.api.bukkit.virtual.game.match.MatchDoc;
import net.astrocube.api.core.http.HttpClient;
import net.astrocube.api.core.message.Channel;
import net.astrocube.api.core.message.Messenger;
import net.astrocube.api.core.model.ModelMeta;
import net.astrocube.api.core.redis.Redis;

import java.util.HashMap;
import java.util.Set;

public class CoreMatchService implements MatchService {

    private @Inject ObjectMapper objectMapper;
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
        teamAssignMessageChannel.sendMessage(() -> teams, new HashMap<>());
    }

    @Override
    public void unAssignPending(String user, String match) throws Exception {
        ObjectNode node = objectMapper.createObjectNode();

        node.put("user", user);
        node.put("match", match);

        Channel<PendingAssignMessage> pendingAssignMessageChannel = messenger.getChannel(PendingAssignMessage.class);
        pendingAssignMessageChannel.sendMessage(objectMapper.readValue(node.toString(), PendingAssignMessage.class), new HashMap<>());
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
        Channel<MatchCleanupMessage> matchCleanupMessageChannel = messenger.getChannel(MatchCleanupMessage.class);
        matchCleanupMessageChannel.sendMessage(() -> "", new HashMap<>());
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
