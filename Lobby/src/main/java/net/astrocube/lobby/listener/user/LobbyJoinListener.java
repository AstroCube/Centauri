package net.astrocube.lobby.listener.user;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import net.astrocube.api.bukkit.lobby.event.LobbyJoinEvent;
import net.astrocube.api.bukkit.lobby.hide.HideJoinProcessor;
import net.astrocube.api.bukkit.lobby.hotbar.LobbyHotbarProvider;
import net.astrocube.api.bukkit.lobby.nametag.LobbyNametagHandler;
import net.astrocube.api.bukkit.packet.PacketHandler;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class LobbyJoinListener implements Listener {

    private @Inject HideJoinProcessor hideJoinProcessor;
    private @Inject LobbyHotbarProvider lobbyHotbarProvider;
    private @Inject LobbyNametagHandler lobbyNametagHandler;
    private @Inject Plugin plugin;

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

        Bukkit.getScheduler().runTask(plugin, () -> plugin.getConfig().getStringList("ambiental.effects").forEach(effect -> {

            try {
                PotionEffectType potionEffectType = PotionEffectType.getByName(effect);
                player.addPotionEffect(
                        new PotionEffect(potionEffectType, Integer.MAX_VALUE, 1, true, false)
                );
            } catch (IllegalArgumentException e) {
                plugin.getLogger().warning("Could not find effect type " + effect);
            }

        }));

        //lobbyNametagHandler.render(player, event.getUser());

    }

}
