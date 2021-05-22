package net.astrocube.commons.core.server;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.client.http.HttpResponse;
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
import net.astrocube.commons.core.http.resolver.RequestExceptionResolverUtil;
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
			Server actual = fetchAndCacheActual();
			this.actual = actual.getId();
			return actual;
		}

		try (Jedis jedis = redis.getRawConnection().getResource()) {
			if (jedis.exists("server:" + actual)) {
				return objectMapper.readValue(jedis.get("server:" + actual), Server.class);
			}
		}

		return fetchAndCacheActual();
	}

	private Server fetchAndCacheActual() throws Exception {
		return httpClient.executeRequestSync(
			modelMeta.getRouteKey() + "/view/me",
			// creating a custom request callable so we can
			// re-use the returned json object and set it in
			// redis
			request -> {
				HttpResponse response = request.execute();
				String json = response.parseAsString();
				int statusCode = response.getStatusCode();

				if (statusCode == 200) {
					// parse the server
					Server actual = objectMapper.readValue(json, Server.class);
					// cache the server info
					try (Jedis jedis = redis.getRawConnection().getResource()) {
						String key = "server:" + actual.getId();
						jedis.set(key, json);
						jedis.expire(key, 600);
					}
					return actual;
				} else {
					throw RequestExceptionResolverUtil.generateException(json, statusCode);
				}
			},
			new RequestOptions(
				RequestOptions.Type.GET,
				""
			)
		);
	}

}
