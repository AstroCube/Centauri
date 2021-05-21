package net.astrocube.commons.bukkit.session;

import net.astrocube.api.bukkit.authentication.event.SessionSwitchBroadcast;
import net.astrocube.api.core.message.MessageListener;
import net.astrocube.api.core.message.MessageMetadata;
import net.astrocube.api.core.session.SessionSwitchWrapper;
import org.bukkit.Bukkit;

public class AuthenticationSuccessHandler implements MessageListener<SessionSwitchWrapper> {

	@Override
	public void handleDelivery(SessionSwitchWrapper message, MessageMetadata properties) {
		Bukkit.getPluginManager().callEvent(new SessionSwitchBroadcast(message));
	}

}
