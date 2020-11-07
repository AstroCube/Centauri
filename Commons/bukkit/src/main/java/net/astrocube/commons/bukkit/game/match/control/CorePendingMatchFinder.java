package net.astrocube.commons.bukkit.game.match.control;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.astrocube.api.bukkit.game.match.control.PendingMatchFinder;
import net.astrocube.api.bukkit.game.matchmaking.MatchmakingRequest;
import net.astrocube.api.core.redis.Redis;
import net.astrocube.api.core.virtual.gamemode.GameMode;
import net.astrocube.api.core.virtual.gamemode.SubGameMode;
import org.bukkit.plugin.Plugin;
import redis.clients.jedis.Jedis;

import java.util.Objects;
import java.util.Set;
import java.util.logging.Level;
import java.util.stream.Collectors;

@Singleton
public class CorePendingMatchFinder implements PendingMatchFinder {

    private final Jedis redis;
    private final ObjectMapper mapper;
    private final Plugin plugin;

    @Inject
    public CorePendingMatchFinder(Redis redis, ObjectMapper mapper, Plugin plugin) {
        this.redis = redis.getRawConnection().getResource();
        this.mapper = mapper;
        this.plugin = plugin;
    }

    @Override
    public Set<MatchmakingRequest> getPendingMatches(GameMode gameMode, SubGameMode subGameMode) {
        return redis.keys("matchmaking:*")
                .stream()
                .map(redis::get)
                .map(key -> {
                    try {
                        return mapper.readValue(key, MatchmakingRequest.class);
                    } catch (JsonProcessingException e) {
                        plugin.getLogger().log(Level.SEVERE, "Error deserializing matchmaking", e);
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .filter(request ->
                                request.getGameMode().equalsIgnoreCase(gameMode.getId()) &&
                                request.getSubGameMode().equalsIgnoreCase(subGameMode.getId())
                ).collect(Collectors.toSet());
    }

}
