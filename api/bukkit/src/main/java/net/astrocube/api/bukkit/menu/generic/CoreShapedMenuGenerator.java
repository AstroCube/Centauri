package net.astrocube.api.bukkit.menu.generic;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import net.astrocube.api.bukkit.menu.GenericHeadHelper;
import net.astrocube.api.bukkit.menu.MenuUtils;
import net.astrocube.api.bukkit.menu.ShapedMenuGenerator;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;

import org.jetbrains.annotations.Nullable;

import team.unnamed.gui.abstraction.item.ItemClickable;
import team.unnamed.gui.core.gui.type.GUIBuilder;

import java.util.Set;
import java.util.function.Function;

@Singleton
public class CoreShapedMenuGenerator implements ShapedMenuGenerator {

	private @Inject GenericHeadHelper genericHeadHelper;

	@Override
	public <E> Inventory generate(Player player,
								  String title,
								  @Nullable Runnable backClick,
								  Class<E> entityClass, Set<E> entities,
								  Function<E, ItemClickable> parser) {
		return GUIBuilder.builderPaginated(entityClass, title)
			.fillBorders(ItemClickable.builderCancellingEvent()
				.setItemStack(MenuUtils.generateStainedPane())
				.build()
			)
			.setEntities(entities)
			.setItemParser(parser)
			.setBounds(10, 43)
			.setItemsPerRow(7)
			.setItemIfNotEntities(genericHeadHelper.generateDecorator(
				genericHeadHelper.getEmptyHead(player, "menus.empty"),
				22
			))
			.setItemIfNotPreviousPage(genericHeadHelper.generateItem(
				genericHeadHelper.backButton(player),
				45,
				ClickType.LEFT,
				backClick
			))
			.setPreviousPageItem(page -> genericHeadHelper.generateItemCancellingEvent(
				genericHeadHelper.getPreviousHead(player, page),
				48,
				ClickType.LEFT
			))
			.setNextPageItem(page -> genericHeadHelper.generateItemCancellingEvent(
				genericHeadHelper.getNextHead(player, page),
				50,
				ClickType.LEFT
			))
			.addItemReplacingPage(page -> genericHeadHelper.generateDecorator(
				genericHeadHelper.getActual(player, page),
				49
			))
			.build();
	}
}
