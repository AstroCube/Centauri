package net.astrocube.commons.bukkit.admin.staff;

import com.google.inject.Inject;
import me.yushust.message.MessageHandler;
import net.astrocube.api.bukkit.menu.ShapedMenuGenerator;
import net.astrocube.api.bukkit.user.staff.OnlineStaffProvider;
import net.astrocube.api.core.virtual.group.Group;
import net.astrocube.commons.bukkit.admin.AdminPanelMenu;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

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
			(p) -> p.openInventory(adminPanelMenu.createAdminPanel(player)),
			getCategories(player)
		);

	}

	private Set<ShapedMenuGenerator.BaseClickable> getCategories(Player player) throws Exception {

		Set<ShapedMenuGenerator.BaseClickable> clickables = new HashSet<>();

		for (Group group : onlineStaffProvider.getGroups()) {

			ItemStack stack = new ItemStack(Material.PAPER);
			ItemMeta meta = stack.getItemMeta();

			meta.setDisplayName(playerMessageHandler.get(player, "groups." + group.getId() + ".name"));
			meta.setLore(playerMessageHandler.getMany(player, "admin-panel.online-staff.category"));

			stack.setItemMeta(meta);

			clickables.add(new ShapedMenuGenerator.BaseClickable() {
				@Override
				public Consumer<Player> getClick() {
					return (p) -> {
						try {
							p.openInventory(adminOnlineGroupMenu.createOnlineStaffMenu(player, group.getId()));
						} catch (Exception e) {
							playerMessageHandler.send(player, "admin-panel.online-staff.error");
							p.closeInventory();
						}
					};
				}

				@Override
				public ItemStack getStack() {
					return stack;
				}
			});
		}

		return clickables;

	}

}