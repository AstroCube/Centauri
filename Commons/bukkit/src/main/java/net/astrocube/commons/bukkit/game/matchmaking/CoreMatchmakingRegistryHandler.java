package net.astrocube.commons.bukkit.game.matchmaking;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import net.astrocube.api.bukkit.game.event.MatchmakingRequestEvent;
import net.astrocube.api.bukkit.game.matchmaking.MatchAssignable;
import net.astrocube.api.bukkit.game.matchmaking.MatchmakingRegistryHandler;
import net.astrocube.api.bukkit.game.matchmaking.MatchmakingRequest;
import net.astrocube.api.core.redis.Redis;
import org.bukkit.Bukkit;
import redis.clients.jedis.Jedis;

import java.util.Date;
import java.util.Optional;

public class CoreMatchmakingRegistryHandler implements MatchmakingRegistryHandler {

    private final ObjectMapper mapper;
    private final Jedis redis;

    public CoreMatchmakingRegistryHandler(ObjectMapper mapper, Redis redis) {
        this.mapper = mapper;
        this.redis = redis.getRawConnection().getResource();
    }

    @Override
    public void generateRequest(MatchAssignable requesters, String gameMode, String subMode) throws JsonProcessingException {
        generateRequest(requesters, gameMode, subMode, null);
    }

    @Override
    public void generateRequest(MatchAssignable requesters, String gameMode, String subMode, ObjectNode criteria) throws JsonProcessingException {

        MatchmakingRequest request = new MatchmakingRequest() {

            public Date getIssuedDate() { return new Date(); }

            public String getGameMode() { return gameMode; }

            public String getSubGameMode() { return subMode; }

            public MatchAssignable getRequesters() { return requesters; }

            public Optional<ObjectNode> getCriteria() { return Optional.ofNullable(criteria); }

        };

        this.redis.set("matchmaking:" + requesters.getResponsible(), mapper.writeValueAsString(request));
        this.redis.expire("matchmaking:" + requesters.getResponsible(), 60);

        Bukkit.getPluginManager().callEvent(new MatchmakingRequestEvent(request));

    }




}
