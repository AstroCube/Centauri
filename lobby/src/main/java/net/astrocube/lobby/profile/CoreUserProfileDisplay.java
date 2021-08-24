package net.astrocube.lobby.profile;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import me.yushust.message.MessageHandler;
import net.astrocube.api.bukkit.lobby.premium.PremiumSelectBook;
import net.astrocube.api.bukkit.lobby.profile.UserProfileDisplay;
import net.astrocube.api.bukkit.translation.mode.AlertModes;
import net.astrocube.api.core.virtual.user.User;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import team.unnamed.gui.abstraction.item.ItemClickable;
import team.unnamed.gui.core.gui.type.GUIBuilder;
import team.unnamed.gui.core.item.type.ItemBuilder;

@Singleton
public class CoreUserProfileDisplay implements UserProfileDisplay {

	public static final String TEXTURES_URL = "https://textures.minecraft.net/texture/";

	private @Inject MessageHandler messageHandler;
	private @Inject CoreLanguageSelectorDisplay languageSelectorDisplay;
	private @Inject PremiumSelectBook premiumSelectBook;

	@Override
	public void openDisplay(User user, Player player) {
		GUIBuilder menuBuilder = GUIBuilder.builder(
			messageHandler.get(player, "lobby.profile.title"),
			4
		)
			.addItem(ItemClickable.builder(4)
				.setItemStack(ItemBuilder.newSkullBuilder(Material.PLAYER_HEAD)
					.setOwner(player.getName())
					.setName(messageHandler.replacing(player, "lobby.profile.head", "%player_name%", player.getName()))
					.setLore(messageHandler.getMany(player, "lobby.profile.head-lore"))
					.build()
				)
				.setAction(event -> {
					messageHandler.sendIn(
						player, AlertModes.INFO,
						"lobby.profile.head-click-message"
					);
					player.closeInventory();

					return true;
				})
				.build()
			)
			.addItem(ItemClickable.builderCancellingEvent(12)
				.setItemStack(ItemBuilder
					.newBuilder(Material.IRON_SWORD)
					.setName(messageHandler.get(player, "lobby.profile.stats"))
					.setLore(messageHandler.getMany(player, "lobby.profile.stats-lore"))
					.build()
				).build()
			)
			.addItem(ItemClickable.builderCancellingEvent(13)
				.setItemStack(ItemBuilder
					.newBuilder(Material.COMPARATOR)
					.setName(messageHandler.get(player, "lobby.profile.options"))
					.setLore(messageHandler.getMany(player, "lobby.profile.options-lore"))
					.build()
				).build()
			)
			.addItem(ItemClickable.builder(14)
				.setItemStack(ItemBuilder
					.newSkullBuilder(Material.PLAYER_HEAD)
					.setUrl(TEXTURES_URL + "c69196b330c6b8962f23ad5627fb6ecce472eaf5c9d44f791f6709c7d0f4dece")
					.setName(messageHandler.get(player, "lobby.profile.language"))
					.setLore(messageHandler.getMany(player, "lobby.profile.language-lore"))
					.build()
				).setAction(event -> {
					languageSelectorDisplay.openDisplay(user, player);
					return true;
				}).build()
			)
			.addItem(ItemClickable.builder(20)
				.setItemStack(ItemBuilder
					.newSkullBuilder(Material.PLAYER_HEAD)
					.setUrl(TEXTURES_URL + "686d3c12cfcbea83a50292a77fc1f08f94c4e69e147f1de6de2e99e8d4efc635")
					.setName(messageHandler.get(player, "lobby.profile.premium"))
					.setLore(messageHandler.getMany(player, "lobby.profile.premium-lore"))
					.build()
				).setAction(event -> {
					player.closeInventory();
					premiumSelectBook.matchDisplay(player);
					return true;
				}).build()
			).addItem(ItemClickable.builderCancellingEvent(21)
				.setItemStack(ItemBuilder
					.newSkullBuilder(Material.PLAYER_HEAD)
					.setUrl(TEXTURES_URL + "deb46126904463f07ecfc972aaa37373a22359b5ba271821b689cd5367f75762")
					.setName(messageHandler.get(player, "lobby.profile.social"))
					.setLore(messageHandler.getMany(player, "lobby.profile.social-lore"))
					.build()
				).build()
			).addItem(ItemClickable.builderCancellingEvent(23)
				.setItemStack(ItemBuilder
					.newBuilder(Material.BEACON)
					.setName(messageHandler.get(player, "lobby.profile.friends"))
					.setLore(messageHandler.getMany(player, "lobby.profile.friends-lore"))
					.build()
				).build()
			)
			.addItem(ItemClickable.builderCancellingEvent(24)
				.setItemStack(ItemBuilder
					.newSkullBuilder(Material.PLAYER_HEAD)
					.setUrl(TEXTURES_URL + "4d42337be0bdca2128097f1c5bb1109e5c633c17926af5fb6fc20000011aeb53")
					.setName(messageHandler.get(player, "lobby.profile.discord"))
					.setLore(messageHandler.get(player, "lobby.profile.discord-lore"))
					.build()
				).build()
			);

		player.openInventory(menuBuilder.build());
	}

}
