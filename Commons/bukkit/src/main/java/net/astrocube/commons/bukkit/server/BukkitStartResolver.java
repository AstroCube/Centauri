package net.astrocube.commons.bukkit.server;

import com.google.inject.Inject;
import net.astrocube.api.bukkit.game.GameControlPair;
import net.astrocube.api.core.cloud.InstanceNameProvider;
import net.astrocube.api.core.http.header.AuthorizationProcessor;
import net.astrocube.api.core.server.GameServerStartManager;
import net.astrocube.api.core.server.ServerConnectionManager;
import net.astrocube.api.core.server.ServerStartResolver;
import net.astrocube.api.core.service.find.FindService;
import net.astrocube.api.core.virtual.gamemode.GameMode;
import net.astrocube.api.core.virtual.gamemode.SubGameMode;
import net.astrocube.api.core.virtual.server.ServerDoc;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.util.Objects;
import java.util.Optional;
import java.util.logging.Level;

public class BukkitStartResolver implements ServerStartResolver {

    private @Inject ServerConnectionManager serverConnectionManager;
    private @Inject AuthorizationProcessor authorizationProcessor;
    private @Inject GameServerStartManager gameServerStartManager;
    private @Inject GameControlPair gameControlPair;
    private @Inject FindService<GameMode> gameModeFindService;
    private @Inject InstanceNameProvider instanceNameProvider;
    private @Inject Plugin plugin;

    @Override
    public void instantiateServer() {
        try {

            final ServerDoc.Type type = ServerDoc.Type.valueOf(plugin.getConfig().getString("server.type"));

            if (type != ServerDoc.Type.GAME) {
                String token = serverConnectionManager.startConnection(
                        instanceNameProvider.getName(),
                        type,
                        plugin.getConfig().getString("server.cluster"),
                        plugin.getConfig().getBoolean("server.sandbox")
                );
                this.authorizationProcessor.authorizeBackend(token.toCharArray());
                return;
            }

            GameMode gameModeDoc = this.gameModeFindService.findSync(
                    plugin.getConfig().getString("game.mode")
            );

            if (gameModeDoc.getSubTypes() == null)
                throw new Exception("Not subModes inside GameMode");

            Optional<SubGameMode> subGameMode =
                    Objects.requireNonNull(gameModeDoc.getSubTypes()).stream().filter(s ->
                            s.getId().equalsIgnoreCase(plugin.getConfig().getString("game.subMode"))
                    ).findAny();

            if (!subGameMode.isPresent())
                throw new Exception("Requested subGameMode not found");

            String token = gameServerStartManager.createGameServer(
                    instanceNameProvider.getName(),
                    type,
                    plugin.getConfig().getString("server.cluster"),
                    plugin.getConfig().getInt("game.running"),
                    plugin.getConfig().getInt("game.total"),
                    gameModeDoc,
                    subGameMode.get(),
                    plugin.getConfig().getBoolean("server.sandbox")
            );
            this.authorizationProcessor.authorizeBackend(token.toCharArray());

            gameControlPair.enablePairing();

        } catch (Exception e) {
            plugin.getLogger().log(Level.SEVERE, "There was an error initializing the server", e);
            Bukkit.shutdown();
        }
    }



}
