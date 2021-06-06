package net.astrocube.commons.bukkit.admin.staff;

import com.google.inject.Inject;

import me.yushust.message.MessageHandler;

import net.astrocube.api.bukkit.menu.GenericHeadHelper;
import net.astrocube.api.bukkit.menu.ShapedMenuGenerator;
import net.astrocube.api.bukkit.user.staff.OnlineStaffProvider;
import net.astrocube.api.core.virtual.user.User;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import team.unnamed.gui.abstraction.item.ItemClickable;

import java.util.function.Function;

public class AdminOnlineGroupMenu {

	private @Inject ShapedMenuGenerator shapedMenuGenerator;
	private @Inject AdminOnlineStaffMenu adminOnlineStaffMenu;
	private @Inject OnlineStaffProvider onlineStaffProvider;
	private @Inject MessageHandler messageHandler;
	private @Inject GenericHeadHelper genericHeadHelper;

	public Inventory createOnlineStaffMenu(Player player, String id) throws Exception {
		return shapedMenuGenerator.generate(
			player,
			messageHandler.get(player, "admin-panel.online-staff.title"),
			() -> {
				try {
					player.openInventory(adminOnlineStaffMenu.createOnlineStaffMenu(player));
				} catch (Exception e) {
					messageHandler.send(player, "admin-panel.online-staff.error");
					player.closeInventory();
				}
			},
			User.class,
			onlineStaffProvider.getFromGroup(id),
			generateParser(player)
		);
	}

	private Function<User, ItemClickable> generateParser(Player player) {
		return user -> ItemClickable.builderCancellingEvent()
			.setItemStack(genericHeadHelper.generateSkull(
				player,
				user,
				messageHandler.replacingMany(
					player, "admin-panel.online-staff.item-layout.lore",
					"%connected%", user.getSession().getLastSeen()
				)
			))
			.build();
	}

}