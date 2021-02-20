package net.astrocube.api.bukkit.game.channel;

import org.bukkit.entity.Player;

public interface MatchMessageBroadcaster {

    void sendMessage(String message, Player player) throws Exception;

}
