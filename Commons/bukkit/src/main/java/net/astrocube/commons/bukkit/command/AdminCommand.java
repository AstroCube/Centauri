package net.astrocube.commons.bukkit.command;

import com.google.inject.Inject;
import me.fixeddev.commandflow.annotated.CommandClass;
import me.fixeddev.commandflow.annotated.annotation.Command;
import me.fixeddev.commandflow.bukkit.annotation.Sender;
import net.astrocube.commons.bukkit.admin.AdminMainPageMenu;
import org.bukkit.entity.Player;

public class AdminCommand implements CommandClass {

    @Inject
    private AdminMainPageMenu adminMainPageMenu;

    @Command(names = {"apv", "app", "admin"})
    public boolean adminCommand(@Sender Player player) {

        player.openInventory(adminMainPageMenu.createAdminPanel(player));
        return true;
    }
}