package net.astrocube.commons.bukkit.party.channel;

import me.fixeddev.inject.ProtectedModule;
import net.astrocube.api.core.message.ChannelBinder;
import net.astrocube.commons.bukkit.party.channel.listener.PartyInvitationListener;
import net.astrocube.commons.bukkit.party.channel.listener.PartyMessageListener;
import net.astrocube.commons.bukkit.party.channel.listener.PartyWrapMessageListener;
import net.astrocube.commons.bukkit.party.channel.message.PartyInvitationMessage;
import net.astrocube.commons.bukkit.party.channel.message.PartyMessage;
import net.astrocube.commons.bukkit.party.channel.message.PartyWarpMessage;

public class PartyChannelModule extends ProtectedModule implements ChannelBinder {

	protected void configure() {

		bindChannel(PartyInvitationMessage.class)
			.registerListener(PartyInvitationListener.class);

		bindChannel(PartyMessage.class)
			.registerListener(PartyMessageListener.class);

		bindChannel(PartyWarpMessage.class)
			.registerListener(PartyWrapMessageListener.class);

	}

}
