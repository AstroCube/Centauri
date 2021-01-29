package net.astrocube.api.bukkit.translation.mode;

import me.yushust.message.send.MessageSender;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class CoreMessenger implements MessageSender<Player> {

    @Override
    public void send(Player player, String mode, String message) {

        Sound sound = AlertModes.SOUNDS.get(mode);

        if (sound != null) {
            player.playSound(player.getLocation(), sound, 1F, 1F);
        }

        player.sendMessage(message);
    }

}
