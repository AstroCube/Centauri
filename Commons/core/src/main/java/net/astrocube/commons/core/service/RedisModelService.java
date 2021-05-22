package net.astrocube.commons.core.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpResponse;
import com.google.inject.Inject;
import net.astrocube.api.core.http.HttpClient;
import net.astrocube.api.core.http.RequestCallable;
import net.astrocube.api.core.http.RequestOptions;
import net.astrocube.api.core.model.Model;
import net.astrocube.api.core.model.ModelMeta;
import net.astrocube.api.core.model.PartialModel;
import net.astrocube.api.core.redis.Redis;
import net.astrocube.api.core.service.create.CreateRequest;
import net.astrocube.api.core.service.find.FindRequest;
import net.astrocube.api.core.service.update.UpdateRequest;
import net.astrocube.commons.core.http.resolver.RequestExceptionResolverUtil;
import redis.clients.jedis.Jedis;

import java.util.HashMap;

@SuppressWarnings("All")
public class RedisModelService<Complete extends Model, Partial extends PartialModel>
	extends CoreModelService<Complete, Partial> {

	@Inject private Redis redis;
	@Inject private HttpClient httpClient;
	private RedisRequestCallable<Complete> redisRequestCallable;
	@Inject private ObjectMapper objectMapper;

	@Inject
	RedisModelService(
		ObjectMapper mapper,
		ModelMeta<Complete, Partial> modelMeta
	) {
		super(mapper, modelMeta);
	}

	@Inject
	public void constructCallable() {
		this.redisRequestCallable = new RedisRequestCallable<>();
	}

	@Override
	public Complete updateSync(UpdateRequest<Partial> request) throws Exception {
		return httpClient.executeRequestSync(
			modelMeta.getRouteKey(),
			redisRequestCallable,
			new RequestOptions(
				RequestOptions.Type.PUT,
				new HashMap<>(),
				this.objectMapper.writeValueAsString(request.getModel()),
				null
			)
		);
	}

	@Override
	public Complete findSync(FindRequest<Complete> findModelRequest) throws Exception {

		String key = modelMeta.getRouteKey() + ":" + findModelRequest.getId();

		try (Jedis jedis = redis.getRawConnection().getResource()) {
			String jsonValue = jedis.get(key);

			if (jedis.exists(key)) {
				return (Complete) objectMapper.readValue(
					jsonValue,
					mapper.constructType(getCompleteType())
				);
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}

		return httpClient.executeRequestSync(
			this.modelMeta.getRouteKey() + "/" + findModelRequest.getId(),
			this.redisRequestCallable,
			new RequestOptions(
				RequestOptions.Type.GET,
				new HashMap<>(),
				"",
				null
			)
		);
	}

	@Override
	public Complete createSync(CreateRequest<Partial> request) throws Exception {
		return httpClient.executeRequestSync(
			this.modelMeta.getRouteKey(),
			this.redisRequestCallable,
			new RequestOptions(
				RequestOptions.Type.POST,
				this.objectMapper.writeValueAsString(request.getModel())
			)
		);
	}

	private class RedisRequestCallable<T extends Model> implements RequestCallable<T> {

		@Override
		public T call(HttpRequest request) throws Exception {

			final HttpResponse response = request.execute();
			final String json = response.parseAsString();

			int statusCode = response.getStatusCode();

			if (statusCode < 400) {
				try (Jedis jedis = redis.getRawConnection().getResource()) {
					T model = (T) objectMapper.readValue(json, mapper.constructType(getCompleteType()));

					String key = modelMeta.getRouteKey() + ":" + model.getId();

					jedis.set(key, json);

					if (modelMeta.getCache() > 0) {
						jedis.expire(key, modelMeta.getCache());
					}

					return model;
				} catch (Exception exception) {
					exception.printStackTrace();
					throw new Exception("Parsing of " + getCompleteType() + " failed");
				}
			} else {
				throw RequestExceptionResolverUtil.generateException(json, statusCode);
			}
		}
	}
}
