package net.astrocube.commons.bukkit.translation.holder;

import me.yushust.message.MessageRepository;
import me.yushust.message.placeholder.PlaceholderProvider;

import org.bukkit.entity.Player;

import org.jetbrains.annotations.Nullable;

public class PlayerPlaceholderReplacer extends PlaceholderProvider<Player> {

    @Override
    protected @Nullable String replace(MessageRepository messageRepository, Player player, String message) {
        if ("player".equals(message)) {
            return player.getName();
        }

        return "";
    }

}