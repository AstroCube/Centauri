package net.astrocube.api.bukkit.menu;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nullable;
import java.util.Set;
import java.util.function.Consumer;

public interface ShapedMenuGenerator {

	/**
	 * Generate default inventory for result displaying
	 * in a paginated and easier way, UX friendly.
	 * @param player    where menu will be used
	 * @param title     of the menu
	 * @param backClick if a back button is needed and which action will perform when clicked.
	 * @param items     to be displayed
	 * @return inventory to be opened
	 */
	Inventory generate(
		Player player,
		String title,
		@Nullable Consumer<Player> backClick,
		Set<BaseClickable> items
	);

	/**
	 * Generate default inventory for result displaying
	 * in a paginated and easier way, UX friendly.
	 * @param player    where menu will be used
	 * @param title     of the menu
	 * @param backClick if a back button is needed and which action will perform when clicked.
	 * @param items     to be displayed
	 * @param page      to open
	 * @return inventory to be opened
	 */
	Inventory generate(
		Player player,
		String title,
		@Nullable Consumer<Player> backClick,
		Set<BaseClickable> items,
		int page
	);

	/**
	 * Compound of stack and click without
	 * explicitly have an slot
	 */
	interface BaseClickable {

		/**
		 * @return action to be performed when clicked
		 */
		Consumer<Player> getClick();

		/**
		 * @return stack to be used
		 */
		ItemStack getStack();

	}

}
