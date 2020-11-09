package net.astrocube.commons.bukkit.game.match.lobby;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.plugin.Plugin;

public class LobbyLocationParser {

    public static Location getLobby() {

        Plugin plugin = Bukkit.getPluginManager().getPlugin("Commons");

        return new Location(
                Bukkit.getWorld("world"),
                Double.parseDouble(plugin.getConfig().getString("game.lobby.x")),
                Double.parseDouble(plugin.getConfig().getString("game.lobby.y")),
                Double.parseDouble(plugin.getConfig().getString("game.lobby.z"))
        );
    }

}
