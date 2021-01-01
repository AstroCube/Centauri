package net.astrocube.commons.bungee.server;

import com.google.inject.Inject;
import net.astrocube.api.core.http.header.AuthorizationProcessor;
import net.astrocube.api.core.server.ServerConnectionManager;
import net.astrocube.api.core.server.ServerStartResolver;
import net.astrocube.api.core.virtual.server.ServerDoc;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;

import java.util.logging.Level;

public class BungeeStartResolver implements ServerStartResolver {

    private @Inject Configuration configuration;
    private @Inject Plugin plugin;
    private @Inject ServerConnectionManager serverConnectionManager;
    private @Inject AuthorizationProcessor authorizationProcessor;

    @Override
    public void instantiateServer() {

        try {
            String token = serverConnectionManager.startConnection(
                    plugin.getProxy().getName(),
                    ServerDoc.Type.BUNGEE,
                    configuration.getString("api.cluster")
            );
            authorizationProcessor.authorizeBackend(token.toCharArray());
        } catch (Exception e) {
            String message = "There was an error authorizing the bungee instance with the backend";
            plugin.getLogger().log(Level.SEVERE, message, e);
            plugin.getProxy().stop(message);
        }

    }

}
