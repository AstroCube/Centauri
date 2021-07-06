package net.astrocube.lobby.selector.gamemode;

import com.google.inject.Inject;

import me.yushust.message.MessageHandler;

import net.astrocube.api.bukkit.lobby.selector.gamemode.GameItemExtractor;
import net.astrocube.api.bukkit.lobby.selector.redirect.ServerRedirect;
import net.astrocube.api.bukkit.lobby.selector.redirect.ServerSwitchStatus;
import net.astrocube.api.core.cloud.CloudModeConnectedProvider;
import net.astrocube.api.core.virtual.gamemode.GameMode;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import team.unnamed.gui.abstraction.item.ItemClickable;

import java.util.ArrayList;
import java.util.List;

public class CoreGameItemExtractor implements GameItemExtractor {

	private @Inject MessageHandler messageHandler;
	private @Inject ServerRedirect serverRedirect;
	private @Inject CloudModeConnectedProvider cloudModeConnectedProvider;

	@Override
	public ItemClickable generate(GameMode gameModeDoc, Player player) {
		ItemStack icon = new ItemStack(Material.DIRT);

		Material exchangeableMaterial = Material.getMaterial(gameModeDoc.getNavigator().toUpperCase());
		if (exchangeableMaterial != null) icon = new ItemStack(exchangeableMaterial);

		ItemMeta iconMeta = icon.getItemMeta();
		List<String> baseLore = new ArrayList<>(messageHandler.getMany(player, "lobby.gameSelector.games." + gameModeDoc.getId() + ".lore").getContents());

		baseLore.add(" ");
		baseLore.add(
			messageHandler.replacing(
				player, "lobby.gameSelector.gadget-playing",
				"%players%", cloudModeConnectedProvider.getGlobalOnline(gameModeDoc)
			)
		);

		iconMeta.setDisplayName(
			messageHandler.get(player, "lobby.gameSelector.games." + gameModeDoc.getId() + ".title")
		);
		iconMeta.setLore(
			baseLore
		);

		iconMeta.addItemFlags(ItemFlag.values());

		icon.setItemMeta(iconMeta);

		ServerSwitchStatus status = ServerSwitchStatus.SUCCESS;

		// TODO: 06/07/2021 check if the game-mode lobby is full

		System.out.println(gameModeDoc.getName() + " : " + gameModeDoc.getLobby() + " : " + Bukkit.getServerName());
		if (gameModeDoc.getName().equalsIgnoreCase(Bukkit.getServerName())) {
			status = ServerSwitchStatus.CYCLIC;
		}

		ServerSwitchStatus finalStatus = status;
		return ItemClickable.builder(gameModeDoc.getSlot())
			.setItemStack(icon)
			.setAction(event -> {
				event.getWhoClicked().closeInventory();
				serverRedirect.redirectPlayer(player, gameModeDoc.getLobby(), finalStatus, true);
				return true;
			}).build();
	}
}