package net.astrocube.commons.bukkit.listener.user;

import com.google.inject.Inject;
import net.astrocube.api.bukkit.game.channel.MatchMessageBroadcaster;
import net.astrocube.api.bukkit.user.display.DisplayMatcher;
import net.astrocube.api.bukkit.user.display.TranslatedFlairFormat;
import net.astrocube.api.core.server.ServerService;
import net.astrocube.api.core.service.find.FindService;
import net.astrocube.api.core.virtual.server.ServerDoc;
import net.astrocube.api.core.virtual.user.User;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.Plugin;

import java.util.logging.Level;


public class UserChatListener implements Listener {

    private @Inject DisplayMatcher displayMatcher;
    private @Inject ServerService serverService;
    private @Inject FindService<User> findService;
    private @Inject MatchMessageBroadcaster matchMessageBroadcaster;
    private @Inject Plugin plugin;

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerAsyncChat(AsyncPlayerChatEvent event) {

        event.setCancelled(true);

        try {
            if (serverService.getActual().getServerType().equals(ServerDoc.Type.GAME)) {
                matchMessageBroadcaster.sendMessage(event.getMessage(), event.getPlayer());
            }
        } catch (Exception e) {
            plugin.getLogger().log(Level.SEVERE, "Error while obtaining actual server mode. Cancelling chat.", e);
            return;
        }

        findService.find(event.getPlayer().getDatabaseIdentifier()).callback(userCallback -> {

            if (!userCallback.isSuccessful()) {
                Bukkit.getOnlinePlayers().forEach(player ->
                        player.sendMessage(
                                event.getPlayer().getDisplayName() + ChatColor.RESET +
                                        ": " + event.getMessage())
                );
            }

            userCallback.ifSuccessful(user -> Bukkit.getOnlinePlayers().forEach(player ->  {

                TranslatedFlairFormat format = displayMatcher.getDisplay(player, user);

                player.sendMessage(
                        format.getPrefix() + " " +
                           ChatColor.WHITE + user.getDisplay() + ChatColor.RESET + ": " +
                           event.getMessage()
                );

            }));

        });

    }

}
