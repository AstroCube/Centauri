package net.astrocube.commons.bukkit.listener.user;

import com.google.inject.Inject;
import net.astrocube.api.bukkit.user.display.DisplayMatcher;
import net.astrocube.api.bukkit.user.display.TranslatedFlairFormat;
import net.astrocube.api.core.service.find.FindService;
import net.astrocube.api.core.virtual.user.User;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;


public class UserChatListener implements Listener {

    private @Inject DisplayMatcher displayMatcher;
    private @Inject FindService<User> findService;

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerAsyncChat(AsyncPlayerChatEvent event) {

        if (!event.isCancelled()) {
            findService.find(event.getPlayer().getDatabaseIdentifier()).callback(userCallback -> {

                if (!userCallback.isSuccessful()) {
                    Bukkit.getOnlinePlayers().forEach(player ->
                            player.sendMessage(
                                    event.getPlayer().getDisplayName() + ChatColor.RESET +
                                            ": " + event.getMessage())
                    );
                }

                userCallback.ifSuccessful(user -> {

                    Bukkit.getOnlinePlayers().forEach(player ->  {

                        TranslatedFlairFormat format = displayMatcher.getDisplay(player, user);

                        player.sendMessage(
                                format.getPrefix() + " " +
                                        ChatColor.WHITE + user.getDisplay() + ChatColor.RESET + ": " +
                                        event.getMessage()
                        );

                    });

                });


            });
        }

        event.setCancelled(true);

    }

}
