package net.astrocube.api.bukkit.game.lobby;

import net.astrocube.api.bukkit.virtual.game.match.Match;
import net.astrocube.api.core.virtual.gamemode.SubGameMode;
import net.astrocube.api.core.virtual.user.User;
import org.bukkit.entity.Player;

public interface LobbySessionModifier {

	/**
	 * Ensure diverse aspects for a player when joins a
	 * match, like message announcement, mode and listeners
	 * locking.
	 * @param user        to be disposed
	 * @param player      to be disposed
	 * @param match       where data will be taken
	 * @param subGameMode where mode data will be taken
	 */
	void ensureJoin(User user, Player player, Match match, SubGameMode subGameMode);

	/**
	 * Ensure diverse aspects for a player when lefts a
	 * match, like message announcement, mode and listeners
	 * locking.
	 * @param user        to be disposed
	 * @param player      to be disposed
	 * @param match       where data will be taken
	 * @param subGameMode where mode data will be taken
	 */
	void ensureDisconnect(User user, Player player, Match match, SubGameMode subGameMode);

}
