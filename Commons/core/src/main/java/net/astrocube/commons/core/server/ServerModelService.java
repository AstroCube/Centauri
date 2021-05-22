package net.astrocube.commons.core.server;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.reflect.TypeToken;
import com.google.inject.Inject;
import net.astrocube.api.core.http.HttpClient;
import net.astrocube.api.core.http.RequestOptions;
import net.astrocube.api.core.model.ModelMeta;
import net.astrocube.api.core.redis.Redis;
import net.astrocube.api.core.server.ServerService;
import net.astrocube.api.core.service.create.CreateRequest;
import net.astrocube.api.core.virtual.server.Server;
import net.astrocube.api.core.virtual.server.ServerDoc;
import net.astrocube.api.core.virtual.server.ServerDoc.Partial;
import net.astrocube.commons.core.http.CoreRequestCallable;
import redis.clients.jedis.Jedis;

@SuppressWarnings("UnstableApiUsage")
public class ServerModelService implements ServerService {

	private @Inject ModelMeta<Server, ServerDoc.Partial> modelMeta;
	private @Inject HttpClient httpClient;
	private @Inject ObjectMapper objectMapper;
	private @Inject Redis redis;
	private String actual = "";

	public String connect(CreateRequest<Partial> request) throws Exception {
		return httpClient.executeRequestSync(
			this.modelMeta.getRouteKey(),
			new CoreRequestCallable<>(TypeToken.of(String.class), this.objectMapper),
			new RequestOptions(
				RequestOptions.Type.POST,
				this.objectMapper.writeValueAsString(request.getModel())
			)
		);
	}

	public void disconnect() throws Exception {
		httpClient.executeRequestSync(
			this.modelMeta.getRouteKey(),
			new CoreRequestCallable<>(TypeToken.of(Void.class), this.objectMapper),
			new RequestOptions(
				RequestOptions.Type.DELETE,
				""
			)
		);

		try (Jedis jedis = redis.getRawConnection().getResource()) {
			jedis.del("server:" + actual);
		}

	}

	@Override
	public Server getActual() throws Exception {

		if (actual.isEmpty()) {
			Server actual = fetchActual();
			try (Jedis jedis = redis.getRawConnection().getResource()) {
				cacheActual(jedis, actual);
			}
			this.actual = actual.getId();
			return actual;
		}

		try (Jedis jedis = redis.getRawConnection().getResource()) {
			if (jedis.exists("server:" + actual)) {
				return objectMapper.readValue(jedis.get("server:" + actual), Server.class);
			} else {
				Server actual = fetchActual();
				cacheActual(jedis, actual);
				return actual;
			}
		}

	}

	private Server fetchActual() throws Exception {
		return httpClient.executeRequestSync(
			this.modelMeta.getRouteKey() + "/view/me",
			new CoreRequestCallable<>(TypeToken.of(Server.class), this.objectMapper),
			new RequestOptions(
				RequestOptions.Type.GET,
				""
			)
		);
	}

	private void cacheActual(Jedis jedis, Server actual) throws Exception {
		String key = "server:" + actual.getId();
		jedis.set(key, objectMapper.writeValueAsString(actual));
		jedis.expire(key, 600);
	}

}
