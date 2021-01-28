package net.astrocube.commons.bukkit.command;

import com.google.inject.Inject;
import me.fixeddev.commandflow.annotated.CommandClass;
import me.fixeddev.commandflow.annotated.annotation.Command;
import me.fixeddev.commandflow.annotated.annotation.OptArg;
import me.fixeddev.commandflow.bukkit.annotation.Sender;
import net.astrocube.commons.bukkit.menu.punishment.PunishmentChooserMenu;
import org.bukkit.entity.Player;

public class PunishCommand implements CommandClass {

    private @Inject PunishmentChooserMenu punishmentChooserMenu;

    @Command(names = "punish")
    public boolean punish(@Sender Player player, @OptArg String punished) {

        if (punished == null) {
            player.sendMessage("Specify the target name!");
            return true;
        }

        player.openInventory(punishmentChooserMenu.createPunishmentChooserMenu(player, punished));
        return true;
    }
}