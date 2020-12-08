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

    private @Inject QueryService<Match> matchQueryService;
    private @Inject QueryService<Server> serverQueryService;
    private @Inject QueryService<GameMap> gameMapQueryService;

    @Override
    public void onEnable() {
        this.loader.load();
        copyDefaults();

        gameMapQueryService.getAll().callback(maps -> {

            System.out.println("GameMap");

            maps.getResponse().get().getFoundModels().forEach(map -> {
                System.out.println(map.getId());
            });
        });


        matchQueryService.getAll().callback(matches -> {

            System.out.println("Matches");

            matches.getResponse().get().getFoundModels().forEach(match -> {
                System.out.println(match.getId());
            });
        });

        serverQueryService.getAll().callback(servers -> {

            System.out.println("Servers");

            servers.getResponse().get().getFoundModels().forEach(server -> {
                System.out.println(server.getId());
            });
        });

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
