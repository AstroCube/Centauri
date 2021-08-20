package net.astrocube.commons.bukkit.virtual;

import net.astrocube.inject.ProtectedModule;
import net.astrocube.commons.core.virtual.VirtualModule;

public class BukkitVirtualModule extends ProtectedModule {

	@Override
	protected void configure() {
		install(new VirtualModule());
		install(new MatchModelModule());
		install(new GameMapModelModule());
		install(new ChannelModelModule());
		install(new PartyModelModule());
	}
}
