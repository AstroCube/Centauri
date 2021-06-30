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

@Singleton
public class CoreMatchmakingGenerator implements MatchmakingGenerator {

	private @Inject MatchmakingRegistryHandler matchmakingRegistryHandler;
	private @Inject Redis redis;
	private @Inject MessageHandler messageHandler;

	@Override
	public void pairMatch(Player player, GameMode mode, SubGameMode subMode) throws Exception {

		String id = player.getUniqueId().toString();

		try (Jedis jedis = redis.getRawConnection().getResource()) {
			if (jedis.exists("Cool-down-Request-Game:" + id)) {
				messageHandler.send(player, "play.matchmaking-request-cool-down");
				return;
			}

			jedis.set("Cooldown-Request-Game:" + id, "true");
			jedis.expire("Cool-down-Request-Game:" + id, 5);
		}

		MatchAssignable assignable = new MatchAssignable(
			player.getDatabaseIdentifier(),
			new HashSet<>() // TODO: Get party
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
