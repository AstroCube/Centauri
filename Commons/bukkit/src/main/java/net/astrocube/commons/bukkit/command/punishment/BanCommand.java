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

@ACommand(names = "ban")
public class BanCommand implements CommandClass {

    private @Inject MessageHandler<Player> messageHandler;
    private @Inject PunishmentHelper punishmentHelper;

    @ACommand(names = "")
    public boolean runMainCommand(@Injected(true) @Sender Player issuer, OfflinePlayer punished, String duration, String reason) {
        if (UserUtils.checkSamePlayer(issuer, punished, messageHandler)) {
            return true;
        }

        punishmentHelper.createBan(issuer, punished, duration, reason);

        return true;
    }

}