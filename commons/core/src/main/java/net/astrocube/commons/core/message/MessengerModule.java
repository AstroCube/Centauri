package net.astrocube.commons.core.message;

import com.google.inject.TypeLiteral;
import com.google.inject.multibindings.Multibinder;
import me.fixeddev.inject.ProtectedModule;
import net.astrocube.api.core.message.ChannelBinding;
import net.astrocube.api.core.message.Messenger;

public class MessengerModule extends ProtectedModule {

	@Override
	protected void configure() {
		Multibinder.newSetBinder(this, new TypeLiteral<ChannelBinding<?>>() {});
		bind(Messenger.class).to(JedisMessenger.class);
	}

}