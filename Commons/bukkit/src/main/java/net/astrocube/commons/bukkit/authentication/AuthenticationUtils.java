package net.astrocube.commons.bukkit.authentication;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;

public class AuthenticationUtils {

    public static Location generateAuthenticationSpawn(FileConfiguration configuration) {
        return new Location(
                Bukkit.getWorlds().get(0),
                configuration.getDouble("authentication.spawn.x"),
                configuration.getDouble("authentication.spawn.y"),
                configuration.getDouble("authentication.spawn.z"),
                configuration.getFloat("authentication.spawn.yaw"),
                configuration.getFloat("authentication.spawn.pitch")
        );
    }

}
