package net.astrocube.api.bukkit.lobby.selector.npc;

import org.bukkit.entity.Player;

public interface LobbyNPCActionHandler {

	/**
	 * Executes click of lobby
	 * @param player  player
	 * @param mode    id
	 * @param subMode id
	 */
	void execute(Player player, String mode, String subMode);

}
