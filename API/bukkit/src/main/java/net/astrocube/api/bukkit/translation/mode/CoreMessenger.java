package net.astrocube.api.bukkit.translation.mode;

import me.yushust.message.mode.Mode;
import me.yushust.message.specific.Messenger;
import org.bukkit.entity.Player;

public class CoreMessenger implements Messenger<Player> {

    @Override
    public void send(Player player, Mode mode, String s) {

        if (mode instanceof AlertMode) {

            AlertMode alertMode = (AlertMode) mode;

            if (alertMode.getSound() != null) {
                player.playSound(player.getLocation(), ((AlertMode) mode).getSound(), 1f, 1f);
            }

        }

        player.sendMessage(s);
    }

}
