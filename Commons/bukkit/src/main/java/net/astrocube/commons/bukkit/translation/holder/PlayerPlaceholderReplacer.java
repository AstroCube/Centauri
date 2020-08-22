package net.astrocube.commons.bukkit.translation.holder;

import me.yushust.message.core.placeholder.PlaceholderReplacer;
import org.bukkit.entity.Player;

public class PlayerPlaceholderReplacer implements PlaceholderReplacer<Player> {

    @Override
    public String replace(Player player, String s) {
        return s.replace("%%player%%", player.getName());
    }

}
