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
    private @Inject HttpClient httpClient;
    private @Inject ModelMeta<Match, MatchDoc.Partial> modelMeta;
    private @Inject Redis redis;
    private final Messenger messenger;

    @Inject
    public CoreMatchService(Messenger messenger) {
        this.messenger = messenger;
    }

    @Override
    public void assignSpectator(String user, String match, boolean join) throws Exception {

        ObjectNode node = objectMapper.createObjectNode();

        node.put("user", user);
        node.put("match", match);
        node.put("join", join);

        Channel<SpectatorAssignMessage> spectatorAssignMessageChannel = messenger.getChannel(SpectatorAssignMessage.class);
        spectatorAssignMessageChannel.sendMessage(objectMapper.readValue(node.toString(), SpectatorAssignMessage.class), new HashMap<>());
    }

    @Override
    public void assignTeams(Set<MatchDoc.Team> teams, String match) throws Exception {
        ObjectNode node = objectMapper.createObjectNode();

        node.putPOJO("teams", teams);
        node.put("match", match);

        Channel<TeamAssignMessage> teamAssignMessageChannel = messenger.getChannel(TeamAssignMessage.class);
        teamAssignMessageChannel.sendMessage(objectMapper.readValue(node.toString(), TeamAssignMessage.class), new HashMap<>());
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
        ObjectNode node = objectMapper.createObjectNode();

        node.putPOJO("pending", pendingRequest);
        node.put("match", match);

        Channel<MatchmakingAssignMessage> matchmakingAssignMessageChannel = messenger.getChannel(MatchmakingAssignMessage.class);
        matchmakingAssignMessageChannel.sendMessage(objectMapper.readValue(node.toString(), MatchmakingAssignMessage.class), new HashMap<>());
    }

    @Override
    public void matchCleanup() throws Exception {
        Channel<MatchCleanupMessage> matchCleanupMessageChannel = messenger.getChannel(MatchCleanupMessage.class);
        matchCleanupMessageChannel.sendMessage(new MatchCleanupMessage() {
            @Override
            public String getMatch() {
                return "";
            }
        }, new HashMap<>());
    }

    @Override
    public void assignVictory(String match, Set<String> winners) throws Exception {
        ObjectNode node = objectMapper.createObjectNode();

        node.put("match", match);
        node.putPOJO("winners", winners);

        Channel<VictoryAssignMessage> victoryAssignMessageChannel = messenger.getChannel(VictoryAssignMessage.class);
        victoryAssignMessageChannel.sendMessage(objectMapper.readValue(node.toString(), VictoryAssignMessage.class), new HashMap<>());
    }

    @Override
    public void disqualify(String match, String user) throws Exception {

        ObjectNode node = objectMapper.createObjectNode();

        node.put("user", user);
        node.put("match", match);

        Channel<MatchDisqualifyMessage> matchDisqualifyMessageChannel = messenger.getChannel(MatchDisqualifyMessage.class);
        matchDisqualifyMessageChannel.sendMessage(objectMapper.readValue(node.toString(), MatchDisqualifyMessage.class), new HashMap<>());
    }

    @Override
    public void privatizeMatch(String requester, String match) throws Exception {
        ObjectNode node = objectMapper.createObjectNode();

        node.put("requester", requester);
        node.put("match", match);

        Channel<MatchPrivatizeMessage> matchPrivatizeMessageChannel = messenger.getChannel(MatchPrivatizeMessage.class);
        matchPrivatizeMessageChannel.sendMessage(objectMapper.readValue(node.toString(), MatchPrivatizeMessage.class), new HashMap<>());
    }

}
