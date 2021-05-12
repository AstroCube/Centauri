package net.astrocube.api.bukkit.game.spectator;

import net.astrocube.api.bukkit.game.exception.GameControlException;
import net.astrocube.api.bukkit.virtual.game.match.Match;
import org.bukkit.entity.Player;

import java.io.IOException;

public interface SpectatorSessionManager {

	void provideFunctions(Player player, Match match) throws GameControlException, IOException;

}
