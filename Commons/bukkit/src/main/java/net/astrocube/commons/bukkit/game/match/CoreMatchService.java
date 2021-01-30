package net.astrocube.commons.bukkit.game.match;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.reflect.TypeToken;
import com.google.inject.Inject;
import net.astrocube.api.bukkit.game.match.MatchService;
import net.astrocube.api.bukkit.game.matchmaking.MatchAssignable;
import net.astrocube.api.bukkit.virtual.game.match.Match;
import net.astrocube.api.bukkit.virtual.game.match.MatchDoc;
import net.astrocube.api.core.http.HttpClient;
import net.astrocube.api.core.http.RequestOptions;
import net.astrocube.api.core.model.ModelMeta;
import net.astrocube.api.core.redis.Redis;
import net.astrocube.commons.core.http.CoreRequestCallable;
import net.astrocube.commons.core.http.CoreRequestOptions;
import net.astrocube.commons.core.service.RedisRequestCallable;

import java.util.HashMap;
import java.util.Set;

public class CoreMatchService implements MatchService {

    private @Inject ObjectMapper objectMapper;
    private @Inject HttpClient httpClient;
    private @Inject ModelMeta<Match, MatchDoc.Partial> modelMeta;
    private @Inject Redis redis;

    @Override
    public void assignSpectator(String user, String match, boolean join) throws Exception {

        ObjectNode node = objectMapper.createObjectNode();

        node.put("user", user);
        node.put("match",  match);
        node.put("join", join);

        httpClient.executeRequestSync(
                this.modelMeta.getRouteKey() + "/spectator",
                new RedisRequestCallable<>(objectMapper, redis, modelMeta),
                new CoreRequestOptions(
                        RequestOptions.Type.POST,
                        new HashMap<>(),
                        this.objectMapper.writeValueAsString(node),
                        null
                )
        );
    }

    @Override
    public void assignTeams(Set<MatchDoc.Team> teams, String match) throws Exception {
        ObjectNode node = objectMapper.createObjectNode();

        node.putPOJO("teams", teams);
        node.put("match",  match);

        httpClient.executeRequestSync(
                this.modelMeta.getRouteKey() + "/teams",
                new RedisRequestCallable<>(objectMapper, redis, modelMeta),
                new CoreRequestOptions(
                        RequestOptions.Type.POST,
                        new HashMap<>(),
                        this.objectMapper.writeValueAsString(node),
                        null
                )
        );
    }

    @Override
    public void unAssignPending(String user, String match) throws Exception {
        ObjectNode node = objectMapper.createObjectNode();

        node.put("user", user);
        node.put("match",  match);

        httpClient.executeRequestSync(
                this.modelMeta.getRouteKey() + "/unassign-pending",
                new RedisRequestCallable<>(objectMapper, redis, modelMeta),
                new CoreRequestOptions(
                        RequestOptions.Type.POST,
                        new HashMap<>(),
                        this.objectMapper.writeValueAsString(node),
                        null
                )
        );
    }

    @Override
    public void assignPending(MatchAssignable pendingRequest, String match) throws Exception {
        ObjectNode node = objectMapper.createObjectNode();

        node.putPOJO("pending", pendingRequest);
        node.put("match",  match);

        httpClient.executeRequestSync(
                this.modelMeta.getRouteKey() + "/pending",
                new RedisRequestCallable<>(objectMapper, redis, modelMeta),
                new CoreRequestOptions(
                        RequestOptions.Type.POST,
                        new HashMap<>(),
                        this.objectMapper.writeValueAsString(node),
                        null
                )
        );
    }

    @Override
    public void matchCleanup() throws Exception {
        httpClient.executeRequestSync(
                this.modelMeta.getRouteKey() + "/cleanup",
                new CoreRequestCallable<>(TypeToken.of(Void.class), objectMapper),
                new CoreRequestOptions(
                        RequestOptions.Type.POST,
                        new HashMap<>(),
                        "",
                        null
                )
        );
    }

    @Override
    public void assignVictory(String match, Set<String> winners) throws Exception {
        httpClient.executeRequestSync(
                this.modelMeta.getRouteKey() + "/validate-winners/" + match,
                new RedisRequestCallable<>(objectMapper, redis, modelMeta),
                new CoreRequestOptions(
                        RequestOptions.Type.POST,
                        new HashMap<>(),
                        this.objectMapper.writeValueAsString(winners),
                        null
                )
        );
    }

    @Override
    public void disqualify(String match, String user) throws Exception {

        ObjectNode node = objectMapper.createObjectNode();

        node.put("user", user);
        node.put("match",  match);

        httpClient.executeRequestSync(
                this.modelMeta.getRouteKey() + "/disqualify",
                new RedisRequestCallable<>(objectMapper, redis, modelMeta),
                new CoreRequestOptions(
                        RequestOptions.Type.POST,
                        new HashMap<>(),
                        this.objectMapper.writeValueAsString(node),
                        null
                )
        );
    }

}
