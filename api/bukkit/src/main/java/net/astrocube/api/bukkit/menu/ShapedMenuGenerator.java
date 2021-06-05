package net.astrocube.api.bukkit.menu;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import team.unnamed.gui.abstraction.item.ItemClickable;

import javax.annotation.Nullable;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;

public interface ShapedMenuGenerator {

	/**
	 * Generate default inventory for result displaying
	 * in a paginated and easier way, UX friendly.
	 *
	 * @param player      Player who will receive the inventory.
	 * @param title       Title which will be displayed in the menu
	 * @param backClick   Backing action
	 * @param entityClass Entity class
	 * @param entities    A Set of the entities to paginate
	 * @param parser      Parser to convert an entity to an ItemClickable
	 * @param <E>         Entity type which will be paginated.
	 * @return An inventory which contains all specified entities perfectly paginated.
	 */
	<E> Inventory generate(
		Player player,
		String title,
		@Nullable Runnable backClick,
		Class<E> entityClass,
		Set<E> entities,
		Function<E, ItemClickable> parser
	);

}
