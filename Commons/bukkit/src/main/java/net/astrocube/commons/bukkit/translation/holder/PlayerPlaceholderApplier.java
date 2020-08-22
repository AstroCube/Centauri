package net.astrocube.commons.bukkit.translation.holder;

import me.yushust.message.core.placeholder.PlaceholderApplier;
import org.bukkit.entity.Player;

public class PlayerPlaceholderApplier implements PlaceholderApplier<Player> {
    @Override
    public String applyPlaceholders(Player player, String s) {
        return s.replace("%%player%%", player.getName());
    }
}
