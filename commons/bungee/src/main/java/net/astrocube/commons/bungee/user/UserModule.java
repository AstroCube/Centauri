package net.astrocube.commons.bungee.user;

import net.astrocube.inject.ProtectedModule;
import net.astrocube.api.core.message.ChannelBinder;
import net.astrocube.api.core.session.SessionSwitchWrapper;

public class UserModule extends ProtectedModule implements ChannelBinder {

	@Override
	public void configure() {
		bindChannel(SessionSwitchWrapper.class);
	}

}
