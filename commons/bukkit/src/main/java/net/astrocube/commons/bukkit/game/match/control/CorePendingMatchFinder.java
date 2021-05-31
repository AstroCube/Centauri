package net.astrocube.commons.bukkit.game.match.control;

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
import redis.clients.jedis.JedisPool;

import java.io.IOException;
import java.util.Objects;
import java.util.Set;
import java.util.logging.Level;
import java.util.stream.Collectors;

@Singleton
public class CorePendingMatchFinder implements PendingMatchFinder {

	private final JedisPool jedisPool;
	private final ObjectMapper mapper;
	private final Plugin plugin;

	@Inject
	public CorePendingMatchFinder(Redis redis, ObjectMapper mapper, Plugin plugin) {
		this.jedisPool = redis.getRawConnection();
		this.mapper = mapper;
		this.plugin = plugin;
	}

	@Override
	public Set<MatchmakingRequest> getPendingMatches(GameMode gameMode, SubGameMode subGameMode) {
		try (Jedis jedis = jedisPool.getResource()) {
			return jedis.keys("matchmaking:*")
				.stream()
				.map(jedis::get)
				.map(key -> {
					try {
						return mapper.readValue(key, MatchmakingRequest.class);
					} catch (IOException e) {
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

}
