package net.astrocube.commons.bukkit.game.match;

import com.google.inject.Inject;
import net.astrocube.api.bukkit.server.ServerDisconnectHandler;
import net.astrocube.api.bukkit.virtual.game.match.Match;
import net.astrocube.api.core.redis.Redis;
import net.astrocube.api.core.server.ServerService;
import net.astrocube.api.core.service.query.QueryService;
import net.astrocube.api.core.virtual.server.Server;
import redis.clients.jedis.Jedis;

public class MatchDisconnectHandler implements ServerDisconnectHandler {

	private static final String MATCH_KEY = "match:";

	@Inject
	private QueryService<Match> queryService;

	@Inject
	private ServerService serverService;

	@Inject
	private Redis redis;

	@Override
	public void execute() {

		try {
			Server server = serverService.getActual();

			queryService.getAll()
				.callback(response -> response.getResponse().ifPresent(matchQueryResult -> matchQueryResult.getFoundModels()
					.forEach(match -> {
						if (match.getServer().equals(server.getId())) {
							try (Jedis jedis = redis.getRawConnection().getResource()) {
								jedis.del(MATCH_KEY + match.getId());
							}
						}
					})));

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
