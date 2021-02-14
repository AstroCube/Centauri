package net.astrocube.commons.bukkit.punishment.freeze;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import me.yushust.message.MessageHandler;
import net.astrocube.api.bukkit.punishment.freeze.FreezeRequestAlerter;
import net.astrocube.api.bukkit.translation.mode.AlertModes;
import org.bukkit.entity.Player;
import org.github.paperspigot.Title;

@Singleton
public class CoreFreezeRequestAlerter implements FreezeRequestAlerter {

    private @Inject MessageHandler messageHandler;

    @Override
    public void alert(Player player) {

        Title title = new Title(
                messageHandler.get(player, "punish.freeze.alert.title"),
                messageHandler.get(player, "punish.freeze.alert.sub")
        );

        player.sendTitle(title);
        messageHandler.sendIn(player, AlertModes.ERROR, "punish.freeze.alert.text");

    }

}
