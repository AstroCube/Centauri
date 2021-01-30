package net.astrocube.api.bukkit.game.match;

import net.astrocube.api.core.virtual.user.User;
import org.bukkit.entity.Player;

public interface MatchRejoiner {

    void rejoinMatch(User user, Player player) throws Exception;

}
