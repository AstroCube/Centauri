package net.astrocube.commons.bukkit.teleport;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.astrocube.api.bukkit.teleport.ServerTeleportRetry;
import net.astrocube.api.core.cloud.CloudTeleport;
import net.astrocube.api.core.message.Channel;
import net.astrocube.api.core.message.Messenger;
import net.astrocube.api.core.player.ProxyKickRequest;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.logging.Level;

@Singleton
public class CoreServerTeleportRetry implements ServerTeleportRetry {

	private @Inject CloudTeleport cloudTeleport;
	private @Inject Plugin plugin;
	private @Inject Messenger messenger;

	@Override
	public void attemptTeleport(String player, String server, int attempt, int maxAttempt) {

		Player online = Bukkit.getPlayer(player);

		if (online == null || !online.isOnline()) {
			return;
		}

		if (server == null) {
			online.sendMessage(ChatColor.RED + "Unable to find empty server");
			return;
		}

		if (attempt > maxAttempt) {
			try {
				messenger.getChannel(ProxyKickRequest.class).sendMessage(new ProxyKickRequest(
					player,
					ChatColor.RED + "Giving up on server switch, please log-in again."
				), new HashMap<>());
			} catch (Exception ex) {
				plugin.getLogger().log(Level.SEVERE, "Error while disconnecting on server switch give-up.");
				online.kickPlayer(ChatColor.RED + "Giving up on server switch, please log-in again.");
			}
			return;
		}

		cloudTeleport.teleportToServer(server, player);

		if (online.isOnline()) {
			Bukkit.getScheduler().runTaskLaterAsynchronously(
				plugin,
				() -> attemptTeleport(player, server, attempt + 1, maxAttempt),
				2 * 20L
			);
		}
	}

	@Override
	public void attemptGroupTeleport(String player, String group, int attempt, int maxAttempt) {
		cloudTeleport.getServerFromGroup(group)
			.ifPresent(server -> attemptTeleport(player, server, attempt, maxAttempt));
	}
}