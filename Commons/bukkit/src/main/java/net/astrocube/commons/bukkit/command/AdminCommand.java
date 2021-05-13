package net.astrocube.commons.bukkit.command;

import com.google.inject.Inject;
import me.fixeddev.commandflow.annotated.CommandClass;
import me.fixeddev.commandflow.annotated.annotation.Command;
import me.fixeddev.commandflow.bukkit.annotation.Sender;
import net.astrocube.commons.bukkit.admin.AdminPanelMenu;
import org.bukkit.entity.Player;

public class AdminCommand implements CommandClass {

	@Inject
	private AdminPanelMenu adminPanelMenu;

	@Command(names = {"apv", "app", "admin"}, permission = "commons.staff.panel")
	public boolean adminCommand(@Sender Player player) {

		player.openInventory(adminPanelMenu.createAdminPanel(player));
		return true;
	}
}