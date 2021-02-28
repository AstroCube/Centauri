package net.astrocube.api.bukkit.game.match.control.menu;

import net.astrocube.api.bukkit.virtual.game.match.Match;
import org.bukkit.entity.Player;

public interface MatchMapSwitcher {

    void openMapMenu(Player player, Match match) throws Exception;

}
