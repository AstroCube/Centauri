package net.astrocube.api.bukkit.punishment.lookup;

import net.astrocube.api.core.virtual.punishment.Punishment;
import net.astrocube.api.core.virtual.user.User;
import org.bukkit.entity.Player;

import java.util.Set;

public interface PunishmentLookupMenu {

	/**
	 * Generate a menu for a player
	 * @param punishments to be used
	 * @param requester   to be used
	 * @param target      of the punishments
	 * @param player      where inventory will be opened
	 * @param page        to use
	 */
	void generateMenu(Set<Punishment> punishments, User requester, User target, Player player, int page);

}
