package net.astrocube.commons.bukkit.listener;

import com.google.inject.Inject;
import net.astrocube.api.core.service.find.FindService;
import net.astrocube.api.core.session.SessionService;
import net.astrocube.api.core.virtual.session.SessionValidateDoc;
import net.astrocube.api.core.virtual.user.User;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;

import javax.annotation.Nullable;
import java.util.logging.Level;

public class UserJoinListener implements Listener {

    private @Inject SessionService sessionService;
    private @Inject FindService<User> userFindService;
    private @Inject Plugin plugin;

    @EventHandler
    public void onUserJoin(PlayerJoinEvent event) {

        Player player = event.getPlayer();

        this.userFindService.find(player.getDatabaseIdentifier()).callback(response -> {
            try {

                /*
                    TODO:
                     - Update player permissible base
                     - Incorporate login message system
                     - Incorporate lobby switch
                */

                if (!response.isSuccessful() || !response.getResponse().isPresent())
                    throw new Exception("User response was not present");

                User user =  response.getResponse().get();

                sessionService.serverSwitch(() -> new SessionValidateDoc.ServerSwitch() {
                    @Override
                    public String getUser() {
                        return user.getId();
                    }

                    @Override
                    public String getServer() {
                        return Bukkit.getServerName().split("-")[0];
                    }

                    @Nullable
                    @Override
                    public String getLobby() {
                        return null;
                    }
                });

            } catch (Exception exception) {
                Bukkit.getScheduler().runTask(plugin, () ->
                        player.kickPlayer(ChatColor.RED + "There was an error processing your login. Please try again later."));
                plugin.getLogger().log(Level.SEVERE, "Could not process player final join.", exception);
            }
        });
    }
}
