package net.astrocube.lobby.premium;

import com.google.inject.Inject;
import me.yushust.message.MessageHandler;
import net.astrocube.api.bukkit.lobby.premium.PremiumConfirmationMenu;
import net.astrocube.api.bukkit.lobby.premium.PremiumStatusSwitcher;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import team.unnamed.gui.abstraction.item.ItemClickable;
import team.unnamed.gui.core.gui.type.GUIBuilder;
import team.unnamed.gui.core.item.type.ItemBuilder;

public class CorePremiumConfirmationMenu implements PremiumConfirmationMenu {

	private @Inject MessageHandler messageHandler;
	private @Inject PremiumStatusSwitcher premiumStatusSwitcher;

	@Override
	public void open(Player player) {

		GUIBuilder builder = GUIBuilder.builder(
			messageHandler.get(player, "premium.confirmation.title"),
			3
		);

		builder.addItem(
			ItemClickable.builder(11)
				.setItemStack(
					ItemBuilder.newBuilder(Material.REDSTONE_BLOCK)
						.setName(messageHandler.get(player, "premium.confirmation.decline"))
						.build()
				)
				.setAction((event) -> {
					event.getWhoClicked().closeInventory();
					return true;
				})
				.build()
		);

		builder.addItem(
			ItemClickable.builder(15)
				.setItemStack(
					ItemBuilder.newBuilder(Material.EMERALD_BLOCK)
						.setName(messageHandler.get(player, "premium.confirmation.accept"))
						.build()
				)
				.setAction((event) -> {
					event.getWhoClicked().closeInventory();
					premiumStatusSwitcher.switchStatus(player);
					return true;
				})
				.build()
		);

		player.openInventory(builder.build());

	}

}
