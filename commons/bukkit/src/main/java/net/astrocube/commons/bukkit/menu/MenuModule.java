package net.astrocube.commons.bukkit.menu;

import net.astrocube.inject.ProtectedModule;
import net.astrocube.api.bukkit.menu.ShapedMenuGenerator;
import net.astrocube.api.bukkit.menu.generic.CoreShapedMenuGenerator;
import net.astrocube.commons.bukkit.admin.AdminMenuModule;

public class MenuModule extends ProtectedModule {

	@Override
	public void configure() {
		install(new AdminMenuModule());
		bind(ShapedMenuGenerator.class).to(CoreShapedMenuGenerator.class);
		expose(ShapedMenuGenerator.class);
	}
}