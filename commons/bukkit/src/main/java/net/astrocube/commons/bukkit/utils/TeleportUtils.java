package net.astrocube.commons.bukkit.utils;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class TeleportUtils {

	public static void loadChunkAndTeleport(Player player, Location location) {
		Chunk chunk = location.getChunk();

		if (!chunk.isLoaded()) {
			chunk.load();
		}

		player.teleport(location);
	}

}
