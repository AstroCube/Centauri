package net.astrocube.commons.bukkit.party;

import net.astrocube.inject.ProtectedModule;
import net.astrocube.api.bukkit.party.PartyMessenger;
import net.astrocube.api.bukkit.party.PartyService;
import net.astrocube.commons.bukkit.party.channel.PartyChannelModule;

public class PartyModule extends ProtectedModule {

	@Override
	protected void configure() {

		install(new PartyChannelModule());
		bind(PartyMessenger.class).to(CorePartyMessenger.class);
		bind(PartyService.class).to(CorePartyService.class);
		expose(PartyService.class);

	}
}
