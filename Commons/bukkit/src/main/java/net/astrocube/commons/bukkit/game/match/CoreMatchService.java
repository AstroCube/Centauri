package net.astrocube.commons.bukkit.game.match;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.reflect.TypeToken;
import com.google.inject.Inject;
import net.astrocube.api.bukkit.game.match.MatchService;
import net.astrocube.api.bukkit.game.matchmaking.MatchAssignable;
import net.astrocube.api.bukkit.virtual.game.match.Match;
import net.astrocube.api.bukkit.virtual.game.match.MatchDoc;
import net.astrocube.api.core.concurrent.ExecutorServiceProvider;
import net.astrocube.api.core.http.HttpClient;
import net.astrocube.api.core.http.RequestOptions;
import net.astrocube.api.core.model.ModelMeta;
import net.astrocube.commons.core.http.CoreRequestCallable;
import net.astrocube.commons.core.http.CoreRequestOptions;
import net.astrocube.commons.core.service.CoreModelService;

import java.util.HashMap;
import java.util.Set;

public class CoreMatchService extends CoreModelService<Match, MatchDoc.Partial> implements MatchService {

    private final ObjectMapper objectMapper;

    @Inject
    CoreMatchService(
            ModelMeta<Match, MatchDoc.Partial> modelMeta,
            HttpClient httpClient,
            ObjectMapper mapper,
            ExecutorServiceProvider executorServiceProvider
    ) {
        super(modelMeta, executorServiceProvider);
        this.httpClient = httpClient;
        this.objectMapper = mapper;
    }


    @Override
    public void assignSpectator(String user, String match, boolean join) throws Exception {

        ObjectNode node = objectMapper.createObjectNode();

        node.put("user", user);
        node.put("match",  match);
        node.put("join", join);

        httpClient.executeRequestSync(
                this.modelMeta.getRouteKey() + "/spectator",
                new CoreRequestCallable<>(TypeToken.of(Void.class), this.objectMapper),
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
                new CoreRequestCallable<>(TypeToken.of(Void.class), this.objectMapper),
                new CoreRequestOptions(
                        RequestOptions.Type.POST,
                        new HashMap<>(),
                        this.objectMapper.writeValueAsString(node),
                        null
                )
        );
    }

    @Override
    public void assignPending(MatchAssignable pendingRequests, String match) throws Exception {
        ObjectNode node = objectMapper.createObjectNode();

        node.putPOJO("pending", pendingRequests);
        node.put("match",  match);

        httpClient.executeRequestSync(
                this.modelMeta.getRouteKey() + "/pending",
                new CoreRequestCallable<>(TypeToken.of(Void.class), this.objectMapper),
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
                new CoreRequestCallable<>(TypeToken.of(Void.class), this.objectMapper),
                new CoreRequestOptions(
                        RequestOptions.Type.GET,
                        new HashMap<>(),
                        "",
                        null
                )
        );
    }

}
