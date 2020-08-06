package net.astrocube.api.core.message;

import com.google.inject.Binder;

public interface ChannelBinder extends Binder {

    default <T extends Message> ChannelBind<T> bindChannel(Class<T> type) {
        MessageDefaults.ChannelName channelName = type.getAnnotation(MessageDefaults.ChannelName.class);
        return new ChannelBind<>(this, type, channelName == null ? type.getSimpleName().toLowerCase() : channelName.value());
    }

}