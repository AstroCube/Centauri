package net.astrocube.commons.bukkit.utils;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;

public final class ChatAlertLibrary {

	private ChatAlertLibrary() {
		throw new UnsupportedOperationException("This class couldn't be instantiated!");
	}

	public static void alertChatError(Player player, @Nullable String message) {
		player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 1f, 1f);
		if (message == null) {
			message = "Error executing the last action, please contact an administrator.";
		}
		player.sendMessage(ChatColor.RED + message);
	}

}
