package net.astrocube.lobby.profile;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import me.yushust.message.MessageHandler;

import net.astrocube.api.bukkit.lobby.profile.UserProfileDisplay;
import net.astrocube.api.bukkit.menu.GenericHeadHelper;
import net.astrocube.api.bukkit.menu.MenuUtils;
import net.astrocube.api.bukkit.translation.LanguageType;
import net.astrocube.api.bukkit.translation.mode.AlertModes;
import net.astrocube.api.core.service.update.UpdateService;
import net.astrocube.api.core.virtual.user.User;
import net.astrocube.api.core.virtual.user.UserDoc;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;

import team.unnamed.gui.abstraction.item.ItemClickable;
import team.unnamed.gui.abstraction.item.ItemClickableBuilder;
import team.unnamed.gui.core.gui.type.GUIBuilder;
import team.unnamed.gui.core.item.type.ItemBuilder;

@Singleton
public class CoreLanguageSelectorDisplay {

	private static final int[] AVAILABLE_SLOTS = {11, 12, 13, 14, 15};

	@Inject private UserProfileDisplay userProfileDisplay;
	@Inject private MessageHandler messageHandler;
	@Inject private UpdateService<User, UserDoc.Partial> updateService;
	@Inject private GenericHeadHelper genericHeadHelper;

	public void openDisplay(User user, Player player) {
		GUIBuilder menuBuilder = GUIBuilder.builder(
			messageHandler.get(player, "lobby.profile.language"),
			3
		)
			.fillBorders(ItemClickable.builderCancellingEvent()
				.setItemStack(MenuUtils.generateStainedPane())
				.build()
			)
			.addItem(genericHeadHelper.generateItem(
				genericHeadHelper.backButton(player),
				18,
				ClickType.LEFT,
				() -> userProfileDisplay.openDisplay(user, player)
			));

		int index = 0;
		for (LanguageType languageType : LanguageType.values()) {
			String currentLanguage = user.getLanguage();
			String abbreviation = languageType.getAbbreviation();

			ItemClickableBuilder itemClickableBuilder = ItemClickable.builder(AVAILABLE_SLOTS[index]);
			StringBuilder itemLorePath = new StringBuilder("lobby.profile.language-type." + abbreviation + ".");
			String displayName = messageHandler.get(player, "lobby.profile.language-type." + abbreviation + ".displayname");

			if (currentLanguage.equals(abbreviation)) {
				itemLorePath.append("already-lore");
				itemClickableBuilder.setAction(event -> true);
			} else {
				itemLorePath.append("lore");
				itemClickableBuilder.setAction(event -> {
					user.setLanguage(abbreviation);
					updateService.update(user).callback(updateResponse -> {
						if (!updateResponse.isSuccessful()) {
							messageHandler.sendIn(player, AlertModes.ERROR, "lobby.profile.change-language.error");
						}

						updateResponse.ifSuccessful(updatedUser -> {
							messageHandler.sendReplacingIn(
								player,
								AlertModes.INFO, "lobby.profile.change-language.success",
								"%language%", displayName
							);
						});
					});

					return true;
				});
			}
			itemClickableBuilder.setItemStack(ItemBuilder.newSkullBuilder(Material.SKULL_ITEM, 1, (byte) 3)
				.setUrl(CoreUserProfileDisplay.TEXTURES_URL + languageType.getSkullUrl())
				.setName(messageHandler.replacing(player, "lobby.profile.language-type." + abbreviation + ".name"))
				.setLore(messageHandler.getMany(player, itemLorePath.toString()))
				.build()
			);

			menuBuilder.addItem(itemClickableBuilder.build());

			index++;
		}

		player.openInventory(menuBuilder.build());
	}

}
