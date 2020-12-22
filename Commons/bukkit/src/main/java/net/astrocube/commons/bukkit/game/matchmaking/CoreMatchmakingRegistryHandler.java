package net.astrocube.commons.bukkit.game.matchmaking;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.inject.Inject;
import net.astrocube.api.bukkit.game.event.matchmaking.MatchmakingRequestEvent;
import net.astrocube.api.bukkit.game.matchmaking.MatchAssignable;
import net.astrocube.api.bukkit.game.matchmaking.MatchmakingRegistryHandler;
import net.astrocube.api.bukkit.game.matchmaking.MatchmakingRequest;
import net.astrocube.api.core.redis.Redis;
import org.bukkit.Bukkit;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Date;
import java.util.Optional;

public class CoreMatchmakingRegistryHandler implements MatchmakingRegistryHandler {

    private final ObjectMapper mapper;
    private final JedisPool jedisPool;

    @Inject
    public CoreMatchmakingRegistryHandler(ObjectMapper mapper, Redis redis) {
        this.mapper = mapper;
        this.jedisPool = redis.getRawConnection();
    }

    @Override
    public void generateRequest(MatchAssignable requesters, String gameMode, String subMode) throws JsonProcessingException {
        generateRequest(requesters, gameMode, subMode, null, null);
    }

    @Override
    public void generateRequest(MatchAssignable requesters, String gameMode, String subMode, String map) throws JsonProcessingException {
        generateRequest(requesters, gameMode, subMode, map, null);
    }

    @Override
    public void generateRequest(MatchAssignable requesters, String gameMode, String subMode, String map, ObjectNode criteria) throws JsonProcessingException {

        try (Jedis jedis = jedisPool.getResource()) {
            MatchmakingRequest request = new MatchmakingRequest() {

                public Date getIssuedDate() { return new Date(); }

                public String getGameMode() { return gameMode; }

                public String getSubGameMode() { return subMode; }

                @Override
                public Optional<String> getMap() {
                    return Optional.ofNullable(map);
                }

                public MatchAssignable getRequesters() { return requesters; }

                public Optional<ObjectNode> getCriteria() { return Optional.ofNullable(criteria); }

            };

            jedis.set("matchmaking:" + requesters.getResponsible(), mapper.writeValueAsString(request));
            jedis.expire("matchmaking:" + requesters.getResponsible(), 60);

            Bukkit.getPluginManager().callEvent(new MatchmakingRequestEvent(request));
        }

    }




}
