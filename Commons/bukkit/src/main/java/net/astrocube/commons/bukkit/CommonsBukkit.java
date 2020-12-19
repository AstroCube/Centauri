package net.astrocube.commons.bukkit;

import com.google.inject.Inject;
import me.fixeddev.inject.ProtectedBinder;
import net.astrocube.api.bukkit.server.ServerDisconnectHandler;
import net.astrocube.api.bukkit.virtual.game.map.GameMap;
import net.astrocube.api.bukkit.virtual.game.match.Match;
import net.astrocube.api.core.loader.Loader;
import net.astrocube.api.core.service.query.QueryService;
import net.astrocube.api.core.virtual.server.Server;
import net.astrocube.commons.bukkit.loader.InjectionLoaderModule;
import org.bukkit.plugin.java.JavaPlugin;

public class CommonsBukkit extends JavaPlugin {

    private @Inject Loader loader;
    private @Inject ServerDisconnectHandler serverDisconnectHandler;

    @Override
    public void onEnable() {
        this.loader.load();
        copyDefaults();
    }

    @Override
    public void onDisable() {
        this.serverDisconnectHandler.execute();
    }

    @Override
    public void configure(ProtectedBinder binder) {
        binder.install(new InjectionLoaderModule());
    }

    public void copyDefaults() {
        getConfig().options().copyDefaults(true);
        saveDefaultConfig();
    }

}
