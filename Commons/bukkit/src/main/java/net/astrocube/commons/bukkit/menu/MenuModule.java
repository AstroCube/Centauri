package net.astrocube.commons.bukkit.menu;

import me.fixeddev.inject.ProtectedModule;
import net.astrocube.api.bukkit.menu.ShapedMenuGenerator;
import net.astrocube.commons.bukkit.admin.AdminMenuModule;
import net.astrocube.commons.bukkit.menu.generic.CoreShapedMenuGenerator;

public class MenuModule extends ProtectedModule {

    @Override
    public void configure() {
        install(new AdminMenuModule());
        bind(ShapedMenuGenerator.class).to(CoreShapedMenuGenerator.class);
    }
}