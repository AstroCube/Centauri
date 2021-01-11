package net.astrocube.api.bukkit.channel;

import java.util.Set;

public interface InterceptorRegistry {

    Set<ChatMessageInterceptor> getInterceptors();

    void register(ChatMessageInterceptor interceptor);

}