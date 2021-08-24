package net.astrocube.commons.bukkit.admin.selector;

import me.yushust.message.MessageHandler;
import net.astrocube.api.bukkit.game.spectator.SpectateRequestAssigner;
import net.astrocube.api.bukkit.menu.ShapedMenuGenerator;
import net.astrocube.api.core.virtual.gamemode.GameMode;
import net.astrocube.api.core.virtual.gamemode.SubGameMode;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import team.unnamed.gui.abstraction.item.ItemClickable;
import team.unnamed.gui.core.item.type.ItemBuilder;

import javax.inject.Inject;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

public class AdminSubGameModeSelectorMenu {

	private @Inject MessageHandler playerMessageHandler;
	private @Inject SpectateRequestAssigner spectateRequestAssigner;
	private @Inject ShapedMenuGenerator shapedMenuGenerator;
	private @Inject AdminGameModeSelectorMenu adminGameModeSelectorMenu;

	public Inventory createSubGameModeSelectorMenu(Player player, GameMode gameMode) {
		return shapedMenuGenerator.generate(
			player,
			playerMessageHandler.get(
				player,
				"admin-panel.subGamemode.title"
			),
			() -> adminGameModeSelectorMenu.createGameModeSelectorMenu(player),
			SubGameMode.class,
			gameMode.getSubTypes(),
			subGameMode -> ItemClickable.builder()
				.setItemStack(ItemBuilder.newBuilder(Material.PAPER)
					.setName(ChatColor.BLUE + playerMessageHandler.get(
						player, "admin-panel.subGamemode.items." + subGameMode.getId()
					))
					.setLore(playerMessageHandler.getMany(
						player, "admin-panel.subGamemode.lore"
					))
					.build()
				)
				.setAction(event -> {
					spectateRequestAssigner.assignRequest(
						gameMode.getId(),
						subGameMode.getId(),
						player.getDatabaseId()
					);

					return true;
				})
				.build()
		);
	}

}