package net.astrocube.commons.bukkit.authentication;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
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

	public static void createSpaceEffect() {

		World world = Bukkit.getWorlds().get(0);

		world.setGameRuleValue("commandBlockOutput", "false");
		world.setGameRuleValue("doDaylightCycle", "false");
		world.setGameRuleValue("doEntityDrops", "false");
		world.setGameRuleValue("doFireTick", "false");
		world.setGameRuleValue("doMobLoot", "false");
		world.setGameRuleValue("doMobSpawning", "false");
		world.setGameRuleValue("doTileDrops", "false");
		world.setGameRuleValue("keepInventory", "true");
		world.setGameRuleValue("mobGriefing", "false");

		world.setTime(19000);

	}

}
