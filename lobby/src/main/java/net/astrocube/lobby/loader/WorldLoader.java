package net.astrocube.lobby.loader;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.astrocube.api.core.loader.Loader;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.plugin.Plugin;

@Singleton
public class WorldLoader implements Loader {

	private @Inject Plugin plugin;

	@Override
	public void load() {

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

		world.setTime(plugin.getConfig().getInt("ambiental.time", 1000));

		world.setWeatherDuration(0);
		world.setThundering(false);

		// yaw and pitch aren't set, this could be an issue
		// plugin.getConfig().getInt("spawn.yaw", 0),
		// plugin.getConfig().getInt("spawn.pitch", 0)
		world.setSpawnLocation(
				plugin.getConfig().getInt("spawn.x", 0),
				plugin.getConfig().getInt("spawn.y", 0),
				plugin.getConfig().getInt("spawn.z", 0)
		);

	}


}
