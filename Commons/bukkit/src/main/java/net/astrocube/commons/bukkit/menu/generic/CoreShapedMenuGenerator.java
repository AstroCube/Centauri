package net.astrocube.commons.bukkit.menu.generic;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.astrocube.api.bukkit.menu.ShapedMenuGenerator;
import net.astrocube.commons.bukkit.menu.GenericHeadHelper;
import net.astrocube.commons.bukkit.menu.MenuUtils;
import net.astrocube.commons.core.utils.Pagination;
import net.astrocube.commons.core.utils.SimplePagination;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import team.unnamed.gui.core.gui.GUIBuilder;

import javax.annotation.Nullable;
import java.util.Set;
import java.util.function.Consumer;

@Singleton
public class CoreShapedMenuGenerator implements ShapedMenuGenerator {

    private @Inject GenericHeadHelper genericHeadHelper;

    @Override
    public Inventory generate(Player player, String title, Consumer<Player> itemClick, Set<ItemStack> items, @Nullable Consumer<Player> backClick, int page) {

        Pagination<ItemStack> pagination = new SimplePagination<>(28, items);

        GUIBuilder builder = GUIBuilder.builder(title);

        int index = 10;
        for (ItemStack stack: pagination.getPage(page)) {

            while (MenuUtils.isMarkedSlot(index)) {
                index++;
            }

            builder.addItem(
                    genericHeadHelper.generateDefaultClickable(
                            stack,
                            index,
                            ClickType.LEFT,
                            itemClick
                    )
            );
        }

        if (pagination.pageExists(page - 1) && (page - 1) != 0) {
            builder.addItem(
                    genericHeadHelper.generateDefaultClickable(
                            genericHeadHelper.getPreviousHead(player, page),
                            49,
                            ClickType.LEFT,
                            clicker -> generate(player, title, itemClick, items, backClick, (page - 1))
                    )
            );
        }

        if (items.isEmpty()) {
            builder.addItem(
                    genericHeadHelper.generateDecorator(
                            genericHeadHelper.getEmptyHead(player, "punishment-expiration-menu.items.empty"),
                            22
                    )
            );
        }

        builder.addItem(
                genericHeadHelper.generateDecorator(genericHeadHelper.getActual(player, page), 49)
        );

        if (backClick != null) {
            builder.addItem(
                    genericHeadHelper.generateDefaultClickable(
                            genericHeadHelper.backButton(player),
                            45,
                            ClickType.LEFT,
                            backClick
                    )
            );
        }

        if (pagination.pageExists(page + 1)) {
            builder.addItem(
                    genericHeadHelper.generateDefaultClickable(
                            genericHeadHelper.getNextHead(player, page),
                            50,
                            ClickType.LEFT,
                            (p) -> generate(player, title, itemClick, items, backClick, (page + 1))
                    )
            );
        }


        return builder.build();
    }

    @Override
    public Inventory generate(Player player, String title, Consumer<Player> itemClick, @Nullable Consumer<Player> backClick, Set<ItemStack> items) {
        return generate(player, title, itemClick, items, backClick, 1);
    }
}
