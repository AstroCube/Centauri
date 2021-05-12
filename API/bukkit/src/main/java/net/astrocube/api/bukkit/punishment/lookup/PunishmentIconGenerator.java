package net.astrocube.api.bukkit.punishment.lookup;

import net.astrocube.api.core.virtual.punishment.Punishment;
import net.astrocube.api.core.virtual.user.User;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public interface PunishmentIconGenerator {

	/**
	 * @param punishment to generate
	 * @param player     to translate
	 * @param user       to translate
	 * @return clickable item
	 */
	ItemStack generateFromPunishment(Punishment punishment, Player player, User user);

}
