package net.astrocube.commons.bukkit.punishment.freeze;

import me.fixeddev.inject.ProtectedModule;
import net.astrocube.api.bukkit.punishment.freeze.FreezeRequestAlerter;
import net.astrocube.api.bukkit.punishment.freeze.FrozenUserProvider;

public class FreezeModule extends ProtectedModule {

	@Override
	public void configure() {
		bind(FreezeRequestAlerter.class).to(CoreFreezeRequestAlerter.class);
		bind(FrozenUserProvider.class).to(CoreFrozenUserProvider.class);
	}

}
