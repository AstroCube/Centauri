package net.astrocube.commons.bukkit.party.channel;

import me.fixeddev.inject.ProtectedModule;
import net.astrocube.api.core.message.ChannelBinder;
import net.astrocube.commons.bukkit.party.channel.listener.PartyInvitationListener;
import net.astrocube.commons.bukkit.party.channel.message.PartyInvitationMessage;

public class PartyChannelModule extends ProtectedModule implements ChannelBinder {

	protected void configure() {
		bindChannel(PartyInvitationMessage.class)
			.registerListener(PartyInvitationListener.class);
	}

}
