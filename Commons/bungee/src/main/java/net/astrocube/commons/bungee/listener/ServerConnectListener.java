package net.astrocube.commons.bungee.listener;

import cloud.timo.TimoCloud.api.TimoCloudAPI;
import cloud.timo.TimoCloud.api.objects.ServerObject;
import com.google.inject.Inject;
import net.astrocube.api.core.virtual.user.User;
import net.astrocube.commons.bungee.configuration.PluginConfigurationHelper;
import net.astrocube.commons.bungee.user.UserProvideHelper;
import net.astrocube.commons.core.cloud.CloudUtils;
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


    private @Inject UserProvideHelper userProvideHelper;
    private @Inject PluginConfigurationHelper configurationHelper;
    private @Inject Plugin plugin;

    @EventHandler
    public void onServerConnect(ServerConnectEvent event) {

        if (event.getReason() == ServerConnectEvent.Reason.JOIN_PROXY) {
            try {

                Optional<User> userOptional = userProvideHelper.getUserByName(event.getPlayer().getName().toLowerCase());
                Optional<Configuration> configuration = Optional.ofNullable(configurationHelper.get());

                if (configuration.isPresent()) {

                    Optional<ServerObject> instance =
                            CloudUtils.getServerFromGroup(configuration.get().getString("redirect.authentication"));

                    if (instance.isPresent()) {

                        Optional<ServerInfo> connectable =
                                Optional.of(ProxyServer.getInstance().getServerInfo(instance.get().getName()));

                        connectable.ifPresent(event::setTarget);
                    }

                    instance.orElseThrow(() -> new Exception("Unable to get available instance"));

                }

                configuration.orElseThrow(() -> new Exception("Unable to get optimal server group"));

            } catch (Exception e) {
                event.getPlayer().disconnect(new TextComponent(
                        ChatColor.RED + "Error when logging in, please try again. \n\n" + ChatColor.GRAY + "Error Type: " + e.getClass().getSimpleName())
                );
                plugin.getLogger().log(Level.SEVERE, "Error while obtaining player from database.", e);
            }
        }

    }

}
