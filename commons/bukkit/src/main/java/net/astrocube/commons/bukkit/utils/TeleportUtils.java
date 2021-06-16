package net.astrocube.commons.bukkit.utils;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class TeleportUtils {

	public static void loadChunkAndTeleport(Player player, Location location) {
		Chunk chunk = location.getChunk();

		if (!chunk.isLoaded()) {
			System.out.println("The chunk is not loaded, loading...");
			chunk.load();
		}

		System.out.println("Teleporting...");
		player.teleport(location);
	}

}
