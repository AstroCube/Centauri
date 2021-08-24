package net.astrocube.commons.bukkit.game.spectator;

import com.google.inject.Inject;
import net.astrocube.api.bukkit.game.match.ActualMatchCache;
import net.astrocube.api.bukkit.game.match.UserMatchJoiner;
import net.astrocube.api.bukkit.virtual.game.match.Match;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.plugin.Plugin;

import java.util.Optional;
import java.util.logging.Level;

public class LobbyActionListener implements Listener {

	private @Inject ActualMatchCache actualMatchCache;
	private @Inject Plugin plugin;

	@EventHandler(priority = EventPriority.MONITOR)
	public void onFoodLevelChange(FoodLevelChangeEvent event) {
		if (isCancellable((Player) event.getEntity())) {
			event.setCancelled(true);
		}
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onEntityDamage(EntityDamageEvent event) {
		if (event.getEntity() instanceof Player && isCancellable((Player) event.getEntity())) {
			event.setCancelled(true);
		}
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onBlockBreak(BlockBreakEvent event) {
		if (isCancellable(event.getPlayer())) {
			event.setCancelled(true);
		}
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onBlockPlace(BlockPlaceEvent event) {
		if (isCancellable(event.getPlayer())) {
			event.setCancelled(true);
		}
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onItemDrop(PlayerDropItemEvent event) {
		if (isCancellable(event.getPlayer())) {
			event.setCancelled(true);
		}
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onInventoryInteract(InventoryClickEvent event) {
		if (isCancellable((Player) event.getWhoClicked())) {
			event.setCancelled(true);
		}
	}

	public boolean isCancellable(Player player) {

		try {

			Optional<Match> matchOptional = actualMatchCache.get(player.getDatabaseId());

			if (matchOptional.isPresent()) {

				UserMatchJoiner.Origin origin =
					UserMatchJoiner.checkOrigin(player.getDatabaseId(), matchOptional.get());

				return origin == UserMatchJoiner.Origin.SPECTATING || origin == UserMatchJoiner.Origin.WAITING;

			}

		} catch (Exception e) {
			plugin.getLogger().log(Level.WARNING, "Error while checking match assignation", e);
		}

		return false;

	}

}
