package net.astrocube.commons.bukkit.party;

import me.fixeddev.inject.ProtectedModule;
import net.astrocube.api.bukkit.party.PartyChatHandler;
import net.astrocube.api.bukkit.party.PartyService;
import net.astrocube.commons.bukkit.party.channel.PartyChannelModule;

public class PartyModule extends ProtectedModule {

	@Override
	protected void configure() {
		bind(PartyService.class).to(CorePartyService.class);
		bind(PartyChatHandler.class).to(CorePartyChatHandler.class);
		expose(PartyService.class);

		install(new PartyChannelModule());
	}
}
