package net.astrocube.commons.bukkit.utils;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class TeleportUtils {

	public static void loadChunkAndTeleport(Player player, Location location, Plugin plugin) {
		Bukkit.getScheduler().runTask(plugin, () -> {
			Chunk chunk = location.getChunk();

			if (!chunk.isLoaded()) {
				System.out.println("The chunk is not loaded, loading...");
				chunk.load();
			}

			System.out.println("Teleporting...");
			player.teleport(location);
		});
	}

}
