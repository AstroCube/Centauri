package net.astrocube.commons.bukkit.game.matchmaking;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import me.yushust.message.MessageHandler;
import net.astrocube.api.bukkit.game.matchmaking.MatchAssignable;
import net.astrocube.api.bukkit.game.matchmaking.MatchmakingGenerator;
import net.astrocube.api.bukkit.game.matchmaking.MatchmakingRegistryHandler;
import net.astrocube.api.core.redis.Redis;
import net.astrocube.api.core.virtual.gamemode.GameMode;
import net.astrocube.api.core.virtual.gamemode.SubGameMode;
import org.bukkit.entity.Player;
import redis.clients.jedis.Jedis;

import java.util.HashSet;
import java.util.Set;

@Singleton
public class CoreMatchmakingGenerator implements MatchmakingGenerator {

	private static final String COOL_DOWN_KEY = "cool-down-request-game:";

	private @Inject MatchmakingRegistryHandler matchmakingRegistryHandler;
	private @Inject Redis redis;
	private @Inject MessageHandler messageHandler;

	@Override
	public void pairMatch(Player player, Set<String> involved, GameMode mode, SubGameMode subMode) throws Exception {

		String id = player.getUniqueId().toString();

		try (Jedis jedis = redis.getRawConnection().getResource()) {
			if (jedis.exists(COOL_DOWN_KEY + id)) {
				messageHandler.send(player, "game.play.matchmaking-request-cool-down");
				return;
			}

			jedis.set(COOL_DOWN_KEY + id, "true");
			jedis.expire(COOL_DOWN_KEY + id, 3);
		}

		MatchAssignable assignable = new MatchAssignable(
			player.getDatabaseIdentifier(),
			involved
		);

		matchmakingRegistryHandler.generateRequest(
			assignable,
			mode.getId(),
			subMode.getId(),
			"",
			null
		);

	}

}
