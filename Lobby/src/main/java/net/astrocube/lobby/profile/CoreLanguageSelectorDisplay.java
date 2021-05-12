package net.astrocube.lobby.profile;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import me.yushust.message.MessageHandler;
import net.astrocube.api.bukkit.lobby.profile.UserProfileDisplay;
import net.astrocube.api.core.virtual.user.User;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import team.unnamed.gui.abstraction.item.ItemClickable;
import team.unnamed.gui.core.gui.type.GUIBuilder;
import team.unnamed.gui.core.item.type.ItemBuilder;

@Singleton
public class CoreLanguageSelectorDisplay {

	@Inject private UserProfileDisplay userProfileDisplay;
	@Inject private MessageHandler messageHandler;

	public void openDisplay(User user, Player player) {

		GUIBuilder menuBuilder = GUIBuilder.builder(
			messageHandler.get(player, "lobby.profile.language"),
			4
		);

		//#region Go back Item
		menuBuilder.addItem(ItemClickable.builder(27).setItemStack(ItemBuilder
			.newSkullBuilder(Material.SKULL_ITEM, 1, (byte) 3)
			.setName(messageHandler.get(player, "lobby.profile.back"))
			.setLore(messageHandler.getMany(player, "lobby.profile.back-lore"))
			.setUrl(CoreUserProfileDisplay.TEXTURES_URL + "37aee9a75bf0df7897183015cca0b2a7d755c63388ff01752d5f4419fc645")
			.build()
		).setAction(event -> {
			event.getWhoClicked().closeInventory();
			userProfileDisplay.openDisplay(user, player);
			return true;
		}).build());
		//#endregion

		player.openInventory(menuBuilder.build());
	}

}
