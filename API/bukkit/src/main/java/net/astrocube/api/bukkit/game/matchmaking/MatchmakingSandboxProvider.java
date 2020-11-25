package net.astrocube.api.bukkit.game.matchmaking;

import org.bukkit.entity.Player;

public interface MatchmakingSandboxProvider {

    void pairMatch(Player player) throws Exception;

}
