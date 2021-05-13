package net.astrocube.commons.bukkit.channel;

import net.astrocube.api.bukkit.channel.ChatMessageInterceptor;
import net.astrocube.api.bukkit.channel.InterceptorRegistry;

import java.util.HashSet;
import java.util.Set;

public class CoreInterceptorRegistry implements InterceptorRegistry {

	private final Set<ChatMessageInterceptor> interceptors = new HashSet<>();

	@Override
	public Set<ChatMessageInterceptor> getInterceptors() {
		return this.interceptors;
	}

	@Override
	public void register(ChatMessageInterceptor interceptor) {
		this.interceptors.add(interceptor);
	}
}