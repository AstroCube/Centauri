package net.astrocube.lobby.listener.user;

import com.google.inject.Inject;
import me.yushust.message.MessageHandler;
import net.astrocube.api.bukkit.lobby.board.ScoreboardProcessor;
import net.astrocube.api.bukkit.lobby.event.LobbyJoinEvent;
import net.astrocube.api.bukkit.lobby.hide.HideJoinProcessor;
import net.astrocube.api.bukkit.lobby.hotbar.LobbyHotbarProvider;
import net.astrocube.api.bukkit.lobby.nametag.LobbyNametagHandler;
import net.astrocube.api.bukkit.lobby.selector.npc.SelectorRegistry;
import net.astrocube.api.bukkit.translation.mode.AlertModes;
import net.astrocube.api.core.redis.Redis;
import net.astrocube.api.core.virtual.user.UserDoc;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import redis.clients.jedis.Jedis;

import java.util.Locale;
import java.util.logging.Level;

public class LobbyJoinListener implements Listener {

    private @Inject HideJoinProcessor hideJoinProcessor;
    private @Inject LobbyHotbarProvider lobbyHotbarProvider;
    private @Inject LobbyNametagHandler lobbyNametagHandler;
    private @Inject ScoreboardProcessor scoreboardProcessor;
    private @Inject SelectorRegistry selectorRegistry;
    private @Inject MessageHandler messageHandler;
    private @Inject Plugin plugin;
    private @Inject Redis redis;

    @EventHandler
    public void onLobbyJoin(LobbyJoinEvent event) {

        Player player = event.getPlayer();

        hideJoinProcessor.process(event.getUser());
        lobbyHotbarProvider.setup(event.getUser(), player);

        player.teleport(
                new Location(
                        Bukkit.getWorlds().get(0),
                        plugin.getConfig().getInt("spawn.x", 0),
                        plugin.getConfig().getInt("spawn.y", 0),
                        plugin.getConfig().getInt("spawn.z", 0),
                        plugin.getConfig().getInt("spawn.yaw", 0),
                        plugin.getConfig().getInt("spawn.pitch", 0)
                )
        );

        Bukkit.getScheduler().runTask(plugin, () -> {

            try {
                if (plugin.getConfig().getBoolean("board")) {
                    scoreboardProcessor.generateBoard(player);
                }
            } catch (Exception e) {
                plugin.getLogger().log(Level.SEVERE, "Could not process user board", e);
            }

            plugin.getConfig().getStringList("ambiental.effects").forEach(effect -> {

                try {
                    PotionEffectType potionEffectType = PotionEffectType.getByName(effect);
                    player.addPotionEffect(
                            new PotionEffect(potionEffectType, Integer.MAX_VALUE, 1, true, false)
                    );
                } catch (IllegalArgumentException e) {
                    plugin.getLogger().warning("Could not find effect type " + effect);
                }

            });

            selectorRegistry.spawnSelectors(player);

        });

        lobbyNametagHandler.render(player, event.getUser());

        if (event.getUser().getSession().getAuthorizeMethod() == UserDoc.Session.Authorization.PASSWORD) {
            try (Jedis jedis = redis.getRawConnection().getResource()) {
                if (jedis.exists("premium:" + player.getName().toLowerCase(Locale.ROOT))) {
                    Bukkit.getScheduler().runTaskLaterAsynchronously(plugin, () ->
                            messageHandler.sendIn(player, AlertModes.MUTED, "premium.reminder"), 60 * 20L);
                }
            }
        }

    }

}
