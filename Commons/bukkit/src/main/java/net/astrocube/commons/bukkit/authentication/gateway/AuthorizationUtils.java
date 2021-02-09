package net.astrocube.commons.bukkit.authentication.gateway;

import me.yushust.message.MessageHandler;
import net.astrocube.api.bukkit.authentication.BasicAuthorization;
import net.astrocube.api.bukkit.translation.mode.AlertModes;
import net.astrocube.api.core.authentication.AuthorizeException;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.logging.Level;

public class AuthorizationUtils {

    public static BasicAuthorization build(Player player, String password) {
        return new BasicAuthorization() {
            @Override
            public String getId() {
                return player.getDatabaseIdentifier();
            }

            @Override
            public String getPassword() {
                return password;
            }

            @Override
            public String getAddress() {
                return player.getAddress().getAddress().toString().replace("/", "");
            }
        };
    }

    public static void checkError(Player player, Exception exception, Plugin plugin, MessageHandler messageHandler) {
        if (exception instanceof AuthorizeException) {
            Bukkit.getScheduler().runTask(plugin, ()-> player.kickPlayer(
                    messageHandler.get(player, "authentication.unauthorized")
                            .replace("%%error%%", exception.getMessage())
            ));
            return;
        }

        messageHandler.sendIn(player, AlertModes.ERROR,"authentication.password-error");
        plugin.getLogger().log(Level.WARNING, "Could not perform user login", exception.getMessage());
    }

}
