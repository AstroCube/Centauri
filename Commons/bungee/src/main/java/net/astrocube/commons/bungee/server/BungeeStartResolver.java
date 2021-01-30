package net.astrocube.commons.bungee.server;

import com.google.inject.Inject;
import net.astrocube.api.core.http.header.AuthorizationProcessor;
import net.astrocube.api.core.server.ServerConnectionManager;
import net.astrocube.api.core.server.ServerStartResolver;
import net.astrocube.api.core.virtual.server.ServerDoc;
import net.astrocube.commons.bungee.configuration.PluginConfigurationHelper;
import net.md_5.bungee.api.plugin.Plugin;
import java.util.logging.Level;

public class BungeeStartResolver implements ServerStartResolver {

    private @Inject PluginConfigurationHelper configurationHelper;
    private @Inject Plugin plugin;
    private @Inject ServerConnectionManager serverConnectionManager;
    private @Inject AuthorizationProcessor authorizationProcessor;

    @Override
    public void instantiateServer() {

        try {
            String token = serverConnectionManager.startConnection(
                    plugin.getProxy().getName(),
                    ServerDoc.Type.BUNGEE,
                    configurationHelper.get().getString("api.cluster")
            );
            authorizationProcessor.authorizeBackend(token.toCharArray());
        } catch (Exception e) {
            String message = "There was an error authorizing the bungee instance with the backend";
            plugin.getLogger().log(Level.SEVERE, message, e);
            plugin.getProxy().stop(message);
        }

    }

}
