package net.astrocube.commons.bukkit.command.punishment;

import com.google.inject.Inject;

import me.fixeddev.ebcm.bukkit.parameter.provider.annotation.Sender;
import me.fixeddev.ebcm.parametric.CommandClass;
import me.fixeddev.ebcm.parametric.annotation.ACommand;
import me.fixeddev.ebcm.parametric.annotation.Injected;

import me.yushust.message.MessageHandler;

import net.astrocube.api.bukkit.punishment.PunishmentHelper;
import net.astrocube.commons.bukkit.utils.UserUtils;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

@ACommand(names = "kick")
public class KickCommand implements CommandClass {

    private @Inject PunishmentHelper punishmentHelper;
    private @Inject MessageHandler<Player> messageHandler;

    public boolean runMainCommand(@Injected(true) @Sender Player issuer, OfflinePlayer punished, String reason) {
        if (UserUtils.checkSamePlayer(issuer, punished, messageHandler)) {
            return true;
        }

        punishmentHelper.createKick(issuer, punished, reason);

        return true;
    }

}