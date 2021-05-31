package net.astrocube.api.core.message;

import com.google.inject.Binder;
import com.google.inject.TypeLiteral;
import com.google.inject.multibindings.Multibinder;

public interface ChannelBinder extends Binder {

	default <T extends Message> ChannelBinding<T> bindChannel(Class<T> type) {
		Message.ChannelName channelName = type.getAnnotation(Message.ChannelName.class);
		String name = channelName == null
			? type.getSimpleName().toLowerCase()
			: channelName.value();

		Multibinder<ChannelBinding<?>> channelBinder
			= Multibinder.newSetBinder(this, new TypeLiteral<ChannelBinding<?>>() {});
		ChannelBinding<T> channelBinding = new ChannelBinding<>(type, name);
		channelBinder.addBinding().toInstance(channelBinding);
		return channelBinding;
	}

}