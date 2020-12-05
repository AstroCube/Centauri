package net.astrocube.commons.bukkit.command;

import com.google.inject.Inject;
import me.fixeddev.ebcm.bukkit.parameter.provider.annotation.Sender;
import me.fixeddev.ebcm.parametric.CommandClass;
import me.fixeddev.ebcm.parametric.annotation.ACommand;
import me.fixeddev.ebcm.parametric.annotation.Injected;
import me.yushust.message.MessageHandler;
import net.astrocube.commons.bukkit.menu.PunishmentChooserMenu;
import org.bukkit.entity.Player;

public class PunishCommand implements CommandClass {

    private @Inject MessageHandler<Player> messageHandler;

    @ACommand(names = "punish")
    public boolean punish(@Injected(true) @Sender Player player, String punished) {

        // TODO: 3/12/2020 Check if the punished target exists.

        player.openInventory(new PunishmentChooserMenu(messageHandler).createPunishmentChooserMenu(player, punished));
        return true;
    }
}