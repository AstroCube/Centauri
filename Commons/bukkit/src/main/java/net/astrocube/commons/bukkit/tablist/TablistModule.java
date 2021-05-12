package net.astrocube.commons.bukkit.tablist;

import me.fixeddev.inject.ProtectedModule;
import net.astrocube.api.bukkit.tablist.TablistCompoundApplier;
import net.astrocube.api.bukkit.tablist.TablistGenerator;

public class TablistModule extends ProtectedModule {

	@Override
	public void configure() {
		bind(TablistGenerator.class).to(CoreTablistGenerator.class);
		bind(TablistCompoundApplier.class).to(CoreTablistCompoundApplier.class);
	}

}
