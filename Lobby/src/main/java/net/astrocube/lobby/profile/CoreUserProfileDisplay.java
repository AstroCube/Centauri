package net.astrocube.lobby.profile;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import me.yushust.message.MessageHandler;
import net.astrocube.api.bukkit.lobby.profile.UserProfileDisplay;
import net.astrocube.api.core.virtual.user.User;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import team.unnamed.gui.abstraction.item.ItemClickable;
import team.unnamed.gui.core.gui.type.GUIBuilder;
import team.unnamed.gui.core.item.type.ItemBuilder;

@Singleton
public class CoreUserProfileDisplay implements UserProfileDisplay {

	public static final String TEXTURES_URL = "https://textures.minecraft.net/texture/";

	private @Inject MessageHandler messageHandler;
	private @Inject CoreLanguageSelectorDisplay languageSelectorDisplay;

	@Override
	public void openDisplay(User user, Player player) {
		GUIBuilder menuBuilder = GUIBuilder.builder(
			messageHandler.get(player, "lobby.profile.title"),
			4
		);

		//#region Decoration Items
		ItemStack head = ItemBuilder.newSkullBuilder(Material.SKULL_ITEM, 1, (byte) 3)
			.setOwner(player.getName())
			.setName(messageHandler.get(player, "lobby.profile.head"))
			.setLore(messageHandler.getMany(player, "lobby.profile.head-lore"))
			.build();

		menuBuilder.addItem(ItemClickable.builder(4).setItemStack(head).build());
		//#endregion

		//#region Stats Item
		menuBuilder.addItem(ItemClickable.builder(12).setItemStack(ItemBuilder
			.newBuilder(Material.IRON_SWORD)
			.setName(messageHandler.get(player, "lobby.profile.stats"))
			.setLore(messageHandler.getMany(player, "lobby.profile.stats-lore"))
			.build()
		).build());
		//#endregion

		//#region Options Item
		menuBuilder.addItem(ItemClickable.builder(13).setItemStack(ItemBuilder
			.newBuilder(Material.REDSTONE_COMPARATOR)
			.setName(messageHandler.get(player, "lobby.profile.options"))
			.setLore(messageHandler.getMany(player, "lobby.profile.options-lore"))
			.build()
		).build());
		//#endregion

		//#region Language Item
		menuBuilder.addItem(ItemClickable.builder(14).setItemStack(ItemBuilder
			.newSkullBuilder(Material.SKULL_ITEM, 1, (byte) 3)
			.setUrl(TEXTURES_URL + "c69196b330c6b8962f23ad5627fb6ecce472eaf5c9d44f791f6709c7d0f4dece")
			.setName(messageHandler.get(player, "lobby.profile.language"))
			.setLore(messageHandler.getMany(player, "lobby.profile.language-lore"))
			.build()
		).setAction(event -> {
			event.getWhoClicked().closeInventory();
			languageSelectorDisplay.openDisplay(user, player);
			return true;
		}).build());
		//#endregion

		//#region Social Item
		menuBuilder.addItem(ItemClickable.builder(21).setItemStack(ItemBuilder
			.newSkullBuilder(Material.SKULL_ITEM, 1, (byte) 3)
			.setUrl(TEXTURES_URL + "deb46126904463f07ecfc972aaa37373a22359b5ba271821b689cd5367f75762")
			.setName(messageHandler.get(player, "lobby.profile.social"))
			.setLore(messageHandler.getMany(player, "lobby.profile.social-lore"))
			.build()
		).build());
		//#endregion

		//#region Friends Item
		menuBuilder.addItem(ItemClickable.builder(23).setItemStack(ItemBuilder
			.newBuilder(Material.BEACON)
			.setName(messageHandler.get(player, "lobby.profile.friends"))
			.setLore(messageHandler.getMany(player, "lobby.profile.friends-lore"))
			.build()
		).build());
		//#endregion

		//#region Discord Link
		menuBuilder.addItem(ItemClickable.builder(24).setItemStack(ItemBuilder
			.newSkullBuilder(Material.SKULL_ITEM, 1, (byte) 3)
			.setUrl(TEXTURES_URL + "4d42337be0bdca2128097f1c5bb1109e5c633c17926af5fb6fc20000011aeb53")
			.setName(messageHandler.get(player, "lobby.profile.discord"))
			.setLore(messageHandler.get(player, "lobby.profile.discord-lore"))
			.build()
		).build());
		//#endregion

		player.openInventory(menuBuilder.build());
	}

}
