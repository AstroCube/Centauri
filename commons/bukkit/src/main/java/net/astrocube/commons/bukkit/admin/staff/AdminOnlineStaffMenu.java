package net.astrocube.commons.bukkit.admin.staff;

import com.google.inject.Inject;

import me.yushust.message.MessageHandler;

import net.astrocube.api.bukkit.menu.ShapedMenuGenerator;
import net.astrocube.api.bukkit.translation.mode.AlertModes;
import net.astrocube.api.bukkit.user.staff.OnlineStaffProvider;
import net.astrocube.api.core.virtual.group.Group;
import net.astrocube.commons.bukkit.admin.AdminPanelMenu;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import team.unnamed.gui.abstraction.item.ItemClickable;
import team.unnamed.gui.core.item.type.ItemBuilder;

import java.util.function.Function;

public class AdminOnlineStaffMenu {

	private @Inject OnlineStaffProvider onlineStaffProvider;
	private @Inject MessageHandler playerMessageHandler;
	private @Inject ShapedMenuGenerator shapedMenuGenerator;
	private @Inject AdminPanelMenu adminPanelMenu;
	private @Inject AdminOnlineGroupMenu adminOnlineGroupMenu;

	public Inventory createOnlineStaffMenu(Player player) throws Exception {
		return shapedMenuGenerator.generate(
			player,
			playerMessageHandler.get(player, "admin-panel.online-staff.title"),
			() -> player.openInventory(adminPanelMenu.createAdminPanel(player)),
			Group.class,
			onlineStaffProvider.getGroups(),
			generateParser(player)
		);
	}

	private Function<Group, ItemClickable> generateParser(Player player) {
		return group -> ItemClickable.builder()
			.setItemStack(ItemBuilder.newBuilder(Material.PAPER)
				.setName(playerMessageHandler.get(player, "groups." + group.getId() + ".name"))
				.setLore(playerMessageHandler.getMany(player, "admin-panel.online-staff.category"))
				.build()
			)
			.setAction(event -> {
				try {
					player.openInventory(adminOnlineGroupMenu.createOnlineStaffMenu(player, group.getId()));
				} catch (Exception e) {
					player.closeInventory();
					playerMessageHandler.sendIn(player, AlertModes.ERROR, "admin-panel.online-staff.error");
					e.printStackTrace();
				}

				return true;
			})
			.build();
	}

}