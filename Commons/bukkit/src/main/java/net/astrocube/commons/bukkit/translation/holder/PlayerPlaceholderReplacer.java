package net.astrocube.commons.bukkit.translation.holder;

import me.yushust.message.ContextRepository;
import me.yushust.message.specific.PlaceholderProvider;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

public class PlayerPlaceholderReplacer implements PlaceholderProvider<Player> {

    @Override
    public @Nullable Object replace(ContextRepository<?> contextRepository, Player player, String s) {
        if ("player".equals(s)) {
            return player.getName();
        }

        return "";
    }
}