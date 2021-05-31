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

	}


}
