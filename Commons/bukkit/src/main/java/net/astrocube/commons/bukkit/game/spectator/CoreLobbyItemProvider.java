package net.astrocube.commons.bukkit.game.spectator;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import me.yushust.message.MessageHandler;
import net.astrocube.api.bukkit.game.spectator.LobbyItemProvider;
import net.astrocube.api.bukkit.user.inventory.nbt.NBTUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

@Singleton
public class CoreLobbyItemProvider implements LobbyItemProvider {

	private @Inject MessageHandler messageHandler;

	@Override
	public void provideBackButton(Player player, int slot) {

		ItemStack stack = NBTUtils.addString(new ItemStack(Material.IRON_DOOR, 1), "actionable", "gc_lobby");
		ItemMeta meta = stack.getItemMeta();

		meta.setDisplayName(messageHandler.get(player, "game.spectator.return.title"));
		meta.setLore(messageHandler.getMany(player, "game.spectator.return.lore"));

		stack.setItemMeta(meta);

		player.getInventory().setItem(slot, stack);

	}

	@Override
	public void provideAdminButton(Player player, int slot) {
		ItemStack stack = NBTUtils.addString(new ItemStack(Material.MAGMA_CREAM, 1), "actionable", "gc_admin_lobby");
		ItemMeta meta = stack.getItemMeta();

		meta.setDisplayName(messageHandler.get(player, "game.admin.lobby.item.title"));
		meta.setLore(messageHandler.getMany(player, "game.admin.lobby.item.lore"));

		stack.setItemMeta(meta);

		player.getInventory().setItem(slot, stack);
	}

}
