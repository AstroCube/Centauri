package net.astrocube.api.bukkit.game.spectator;

import com.fasterxml.jackson.core.JsonProcessingException;
import net.astrocube.api.bukkit.game.exception.GameControlException;
import net.astrocube.api.bukkit.virtual.game.match.Match;
import org.bukkit.entity.Player;

public interface SpectatorSessionManager {

    void provideFunctions(Player player, Match match) throws GameControlException, JsonProcessingException;

}
