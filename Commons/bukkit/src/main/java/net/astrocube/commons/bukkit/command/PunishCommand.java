package net.astrocube.commons.bukkit.command;

import me.fixeddev.ebcm.bukkit.parameter.provider.annotation.Sender;
import me.fixeddev.ebcm.parametric.CommandClass;
import me.fixeddev.ebcm.parametric.annotation.ACommand;
import me.fixeddev.ebcm.parametric.annotation.Injected;
import net.astrocube.commons.bukkit.menu.MainStaffSanctionsMenu;
import org.bukkit.entity.Player;

public class PunishCommand implements CommandClass {

    @ACommand(names = "punish")
    public boolean punish(@Injected(true) @Sender Player player, String punished) {

        // TODO: 3/12/2020 Check if the punished target exists.

        player.openInventory(new MainStaffSanctionsMenu().mainSanctionsMenu(punished));
        return true;
    }
}
