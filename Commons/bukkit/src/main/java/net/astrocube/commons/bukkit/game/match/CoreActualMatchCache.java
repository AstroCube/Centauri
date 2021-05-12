package net.astrocube.commons.bukkit.game.match;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.astrocube.api.bukkit.game.exception.GameControlException;
import net.astrocube.api.bukkit.game.match.ActualMatchCache;
import net.astrocube.api.bukkit.game.match.ActualMatchProvider;
import net.astrocube.api.bukkit.virtual.game.match.Match;
import net.astrocube.api.core.redis.Redis;
import net.astrocube.api.core.service.find.FindService;
import org.bukkit.plugin.Plugin;
import redis.clients.jedis.Jedis;

import java.util.Optional;
import java.util.logging.Level;

@Singleton
public class CoreActualMatchCache implements ActualMatchCache {

	private @Inject ActualMatchProvider actualMatchProvider;
	private @Inject FindService<Match> findService;
	private @Inject Plugin plugin;
	private @Inject Redis redis;

	@Override
	public Optional<Match> get(String id) throws Exception {
		try (Jedis jedis = redis.getRawConnection().getResource()) {
			String matchId = jedis.get("actualMatch:" + id);

			if (matchId != null) {
				return Optional.of(findService.findSync(matchId));
			} else {
				return obtainFromProvider(id, jedis);
			}

		} catch (Exception e) {
			throw new GameControlException("Error while opening redis cache connection");
		}

	}

	@Override
	public void remove(String id) {
		try (Jedis jedis = redis.getRawConnection().getResource()) {
			jedis.del("actualMatch:" + id);
		} catch (Exception e) {
			plugin.getLogger().log(Level.WARNING, "Error while removing match from cache", e);
		}
	}

	/**
	 * Obtains match directly from provider
	 * @param id    of user match
	 * @param jedis connection
	 * @return optional of match
	 */
	private Optional<Match> obtainFromProvider(String id, Jedis jedis) throws Exception {

		Optional<Match> match = actualMatchProvider.provide(id);

		if (match.isPresent()) {
			jedis.set("actualMatch:" + id, match.get().getId());
			jedis.expire("actualMatch:" + id, 120);
			return match;
		}

		return Optional.empty();

	}

}
