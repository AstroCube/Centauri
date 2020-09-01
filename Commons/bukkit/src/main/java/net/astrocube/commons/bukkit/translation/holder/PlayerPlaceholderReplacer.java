package net.astrocube.commons.bukkit.translation.holder;

import me.yushust.message.core.intercept.InterceptContext;
import me.yushust.message.core.placeholder.PlaceholderProvider;

import org.bukkit.entity.Player;

import org.jetbrains.annotations.Nullable;

public class PlayerPlaceholderReplacer implements PlaceholderProvider<Player> {

    @Override
    public String[] getPlaceholders() {
        return new String[] {
            "player"
        };
    }

    @Override
    public @Nullable String replace(InterceptContext<Player> interceptContext, String message) {
        Player player = interceptContext.getEntity();

        if ("player".equals(message)) {
            return player.getName();
        }

        return "";
    }
}
