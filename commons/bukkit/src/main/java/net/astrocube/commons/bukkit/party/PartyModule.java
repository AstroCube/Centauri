package net.astrocube.commons.bukkit.party;

import me.fixeddev.inject.ProtectedModule;
import net.astrocube.api.bukkit.party.PartyMessenger;
import net.astrocube.api.bukkit.party.PartyService;
import net.astrocube.commons.bukkit.party.channel.PartyChannelModule;

public class PartyModule extends ProtectedModule {

	@Override
	protected void configure() {
		bind(PartyService.class).to(CorePartyService.class);
		bind(PartyMessenger.class).to(CorePartyMessenger.class);
		expose(PartyService.class);

		install(new PartyChannelModule());
	}
}
