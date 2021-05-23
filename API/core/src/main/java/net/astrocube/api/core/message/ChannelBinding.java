package net.astrocube.api.core.message;

import java.util.HashSet;
import java.util.Set;

/**
 * Represents a channel configuration object.
 * These objects are created and modified in Guice's
 * Injector configuration time.
 * @param <T> The channel message type
 */
public class ChannelBinding<T extends Message> {

	private final Class<T> type;
	private final String name;

	/** The listener bindings */
	private final Set<Class<? extends MessageListener<T>>> listenerBindings = new HashSet<>();

	public ChannelBinding(Class<T> type, String name) {
		this.name = name;
		this.type = type;
	}

	public Class<T> getType() {
		return type;
	}

	public String getName() {
		return name;
	}

	public Set<Class<? extends MessageListener<T>>> getListenerBindings() {
		return listenerBindings;
	}

	public void registerListener(Class<? extends MessageListener<T>> listenerType) {
		listenerBindings.add(listenerType);
	}

	@Override
	public String toString() {
		return name;
	}

}