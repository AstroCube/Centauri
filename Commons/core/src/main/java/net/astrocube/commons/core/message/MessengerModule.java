package net.astrocube.commons.core.message;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.multibindings.Multibinder;
import me.fixeddev.inject.ProtectedModule;
import net.astrocube.api.core.concurrent.ExecutorServiceProvider;
import net.astrocube.api.core.message.ChannelMeta;
import net.astrocube.api.core.message.MessageHandler;
import net.astrocube.api.core.message.Messenger;
import net.astrocube.api.core.redis.Redis;

import java.util.Set;

public class MessengerModule extends ProtectedModule {

    @Override
    protected void configure() {
        Multibinder.newSetBinder(this, ChannelMeta.class);
        Multibinder.newSetBinder(this, MessageHandler.class);
    }

    @Provides
    @Singleton
    Messenger messenger(Redis redis, ObjectMapper mapper,
                        ExecutorServiceProvider executorServiceProvider, Set<ChannelMeta> channelMetas, Set<MessageHandler> handlers) {
        System.out.println(executorServiceProvider.getRegisteredService());
        return new JedisMessenger(redis, mapper, executorServiceProvider.getRegisteredService(), channelMetas, handlers);
    }
}