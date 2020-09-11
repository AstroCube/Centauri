package net.astrocube.commons.bukkit.command;

import me.fixeddev.ebcm.parametric.CommandClass;
import me.fixeddev.ebcm.parametric.annotation.ACommand;
import me.fixeddev.ebcm.parametric.annotation.Injected;

import org.bukkit.command.CommandSender;

@ACommand(names = {"punishments", "punishment", "punish"})
public class PunishmentCommand implements CommandClass {

    @ACommand(names = "")
    public boolean runPunishCommand(@Injected(true) CommandSender sender) {
        return false;
    }

}