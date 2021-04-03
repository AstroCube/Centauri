package net.astrocube.commons.bungee.listener;

import com.google.inject.Inject;
import net.astrocube.api.core.message.Channel;
import net.astrocube.api.core.message.Messenger;
import net.astrocube.api.core.redis.Redis;
import net.astrocube.api.core.session.MojangValidate;
import net.astrocube.api.core.virtual.user.User;
import net.astrocube.api.core.virtual.user.UserDoc;
import net.astrocube.commons.bungee.user.UserProvideHelper;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.PendingConnection;
import net.md_5.bungee.api.event.LoginEvent;
import net.md_5.bungee.api.event.PreLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.event.EventHandler;
import org.apache.commons.codec.Charsets;
import redis.clients.jedis.Jedis;

import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

public class PreLoginListener implements Listener {

    private @Inject Plugin plugin;
    private @Inject UserProvideHelper userProvideHelper;
    private @Inject MojangValidate mojangValidate;
    private @Inject Redis redis;

    @EventHandler
    public void onPreLogin(LoginEvent event) {

        PendingConnection connection = event.getConnection();
        event.registerIntent(plugin);

        try {

            User user = userProvideHelper.getUserByName(event.getConnection().getName())
                    .orElseThrow(() -> new Exception("Error while obtaining user"));

            if (
                    connection.getUniqueId() != null &&
                    mojangValidate.validateUUID(connection.getName(), connection.getUniqueId())
            ) {

                try (Jedis jedis = redis.getRawConnection().getResource()) {
                    jedis.set("premium:" + user.getId(), "1");
                } catch (Exception e) {
                    throw new Exception("Unable to store premium state");
                }

            }

            if (user.getSession().getAuthorizeMethod() != UserDoc.Session.Authorization.PREMIUM) {
                connection.setOnlineMode(false);
                connection
                        .setUniqueId(UUID.nameUUIDFromBytes(("OfflinePlayer:" + connection.getName())
                        .getBytes(Charsets.UTF_8)));
            }


        } catch (Exception e) {
            plugin.getLogger().log(Level.WARNING, "[Commons] There was an error logging a player.", e);
            connection.disconnect(
                    new TextComponent(ChatColor.RED + "Error when logging in, please try again. \n\n" + ChatColor.GRAY + "Error Type: " + e.getClass().getSimpleName())
            );
        }

        event.completeIntent(plugin);

    }

}
