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
                (float) configuration.getDouble("authentication.spawn.yaw"),
                (float) configuration.getDouble("authentication.spawn.pitch")
        );
    }

}
