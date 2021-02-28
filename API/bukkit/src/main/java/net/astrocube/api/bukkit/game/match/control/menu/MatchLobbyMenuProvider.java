package net.astrocube.api.bukkit.game.match.control.menu;

import org.bukkit.entity.Player;

public interface MatchLobbyMenuProvider {

    void create(Player player) throws Exception;

}
