package net.astrocube.api.bukkit.lobby.hide;

import net.astrocube.api.core.virtual.user.User;

public interface HideStatusModifier {

	/**
	 * Applies the selected {@link HideCompound} to the user
	 * @param user to be affected
	 */
	void globalApply(User user);

	/**
	 * Applies the selected {@link HideCompound} to the user
	 * @param user     to be affected
	 * @param target   to be checked
	 * @param compound to be applied
	 */
	void individualApply(User user, User target, HideCompound compound);

	/**
	 * Restore the user visibility to everyone
	 * @param user to be restored
	 */
	void restore(User user);

}
