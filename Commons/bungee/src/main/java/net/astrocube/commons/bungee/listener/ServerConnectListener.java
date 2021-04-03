package net.astrocube.commons.bungee.listener;

import com.google.inject.Inject;
import net.astrocube.api.core.cloud.CloudStatusProvider;
import net.astrocube.api.core.cloud.CloudTeleport;
import net.astrocube.api.core.virtual.user.User;
import net.astrocube.api.core.virtual.user.UserDoc;
import net.astrocube.commons.bungee.configuration.PluginConfigurationHelper;
import net.astrocube.commons.bungee.user.UserProvideHelper;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.event.ServerConnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.event.EventHandler;

import java.util.Optional;
import java.util.logging.Level;

public class ServerConnectListener implements Listener {

    private @Inject CloudTeleport cloudTeleport;
    private @Inject CloudStatusProvider cloudStatusProvider;
    private @Inject PluginConfigurationHelper configurationHelper;
    private @Inject Plugin plugin;

    @EventHandler
    public void onServerConnect(ServerConnectEvent event) {

        if (event.getReason() == ServerConnectEvent.Reason.JOIN_PROXY) {
            try {

                Configuration configuration = Optional.ofNullable(configurationHelper.get())
                        .orElseThrow(() -> new Exception("Unable to get optimal server group"));

                if (cloudStatusProvider.hasCloudHooked()) {

                    ServerInfo connectable =
                            Optional.of(ProxyServer.getInstance().getServerInfo(
                                    cloudTeleport.getServerFromGroup(
                                            configuration.getString("redirect.authentication")
                                    )
                            )).orElseThrow(() -> new Exception("Unable to connect"));

                    event.setTarget(connectable);

                }

            } catch (Exception e) {
                event.getPlayer().disconnect(new TextComponent(
                        ChatColor.RED + "Error when logging in, please try again. \n\n" + ChatColor.GRAY + "Error Type: " + e.getClass().getSimpleName())
                );
                plugin.getLogger().log(Level.SEVERE, "Error while obtaining player from database.", e);
            }
        }

    }

}
