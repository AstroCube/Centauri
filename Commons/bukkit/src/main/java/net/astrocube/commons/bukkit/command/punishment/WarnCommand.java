package net.astrocube.commons.bukkit.command.punishment;

import com.google.inject.Inject;

import me.fixeddev.ebcm.bukkit.parameter.provider.annotation.Sender;
import me.fixeddev.ebcm.parametric.CommandClass;
import me.fixeddev.ebcm.parametric.annotation.ACommand;
import me.fixeddev.ebcm.parametric.annotation.Injected;

import net.astrocube.api.bukkit.punishment.PunishmentHelper;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

@ACommand(names = "warn")
public class WarnCommand implements CommandClass {

    private @Inject PunishmentHelper punishmentHelper;

    @ACommand(names = "")
    public boolean runMainCommand(@Injected(true) @Sender Player issuer, OfflinePlayer punished, String reason) {
        punishmentHelper.createWarn(issuer, (Player) punished, reason);

        return true;
    }

}