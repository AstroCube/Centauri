package net.astrocube.commons.core.message;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import net.astrocube.api.core.concurrent.ExecutorServiceProvider;
import net.astrocube.api.core.message.*;
import net.astrocube.api.core.redis.Redis;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPubSub;

import java.util.*;

@Singleton
public class JedisMessenger implements Messenger {

	public static final String CHANNEL_NAME = "centauri_redis";

	private final ObjectMapper mapper;
	private final JedisPool messengerPool;
	private final Jedis listenerConnection;

	private final Map<String, JedisChannel<?>> channelsByName = new HashMap<>();
	private final Map<Class<? extends Message>, JedisChannel<? extends Message>> channels = new HashMap<>();

	@Inject
	public JedisMessenger(
		Injector injector,
		Redis redis,
		ObjectMapper mapper,
		ExecutorServiceProvider executorServiceProvider,
		Set<ChannelBinding<?>> channelBindings // multi-bound by ChannelBinder
	) {
		this.mapper = mapper;
		this.listenerConnection = redis.getListenerConnection();
		this.messengerPool = redis.getRawConnection();

		for (ChannelBinding<?> channelBinding : channelBindings) {
			instantiateChannel(injector, channelBinding);
		}
		JedisPubSub subscriber = new JedisSubscriber(mapper, channelsByName);
		executorServiceProvider.getRegisteredService()
			.submit(() -> listenerConnection.subscribe(subscriber, CHANNEL_NAME));
	}

	/**
	 * Type-safe method responsible of instantiating all the
	 * listeners bindings ({@link ChannelBinding#getListenerBindings()})
	 * and creating and registering the real {@link Channel}
	 */
	private <T extends Message> void instantiateChannel(
		Injector injector,
		ChannelBinding<T> channelBinding
	) {

		// listener instantiation
		Set<MessageListener<T>> listeners = new HashSet<>();
		for (Class<? extends MessageListener<T>> listenerType
			: channelBinding.getListenerBindings()) {
			listeners.add(injector.getInstance(listenerType));
		}

		// channel creation
		JedisChannel<T> channel = new JedisChannel<>(
			channelBinding,
			this.messengerPool,
			this.mapper,
			listeners
		);

		// channel registration
		channelsByName.put(channel.getName(), channel);
		channels.put(channelBinding.getType(), channel);
	}

	@Override
	public <T extends Message> Channel<T> getChannel(Class<T> type) {
		JedisChannel<T> channel = (JedisChannel<T>) this.channels.get(type);

		if (channel == null) {
			throw new IllegalArgumentException("The channel type " + type.getSimpleName() + " is not registered");
		}

		return channel;
	}
}