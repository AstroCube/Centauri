package net.astrocube.commons.bukkit.menu.generic;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.astrocube.api.bukkit.menu.ShapedMenuGenerator;
import net.astrocube.commons.bukkit.menu.GenericHeadHelper;
import net.astrocube.commons.bukkit.menu.MenuUtils;
import net.astrocube.commons.core.utils.Pagination;
import net.astrocube.commons.core.utils.SimplePagination;
import org.bukkit.craftbukkit.v1_8_R3.creator.hologram.CraftHologram;
import org.bukkit.creator.hologram.Hologram;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import team.unnamed.gui.abstraction.item.ItemClickable;
import team.unnamed.gui.core.gui.GUIBuilder;

import javax.annotation.Nullable;
import java.util.Set;
import java.util.function.Consumer;

@Singleton
public class CoreShapedMenuGenerator implements ShapedMenuGenerator {

    private @Inject GenericHeadHelper genericHeadHelper;


    @Override
    public Inventory generate(Player player, String title, @Nullable Consumer<Player> backClick, Set<BaseClickable> items) {
        return generate(player, title, backClick, items, 1);
    }

    @Override
    public Inventory generate(Player player, String title, @Nullable Consumer<Player> backClick, Set<BaseClickable> items, int page) {


        Pagination<BaseClickable> pagination = new SimplePagination<>(28, items);

        GUIBuilder builder = GUIBuilder.builder(title);

        MenuUtils.generateFrame(builder);

        int index = 10;
        for (BaseClickable stack: pagination.getPage(page)) {

            while (MenuUtils.isMarkedSlot(index)) {
                index++;
            }

            builder.addItem(generateClick(stack, index));
            index++;
        }

        if (pagination.pageExists(page - 1) && (page - 1) != 0) {
            builder.addItem(
                    genericHeadHelper.generateDefaultClickable(
                            genericHeadHelper.getPreviousHead(player, page),
                            48,
                            ClickType.LEFT,
                            clicker -> clicker.openInventory(generate(clicker, title, backClick, items, (page - 1)))
                    )
            );
        }

        if (items.isEmpty()) {
            builder.addItem(
                    genericHeadHelper.generateDecorator(
                            genericHeadHelper.getEmptyHead(player, "menus.empty"),
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
                            clicker -> clicker.openInventory(generate(clicker, title, backClick, items, (page + 1)))
                    )
            );
        }

        return builder.build();

    }

    private ItemClickable generateClick(BaseClickable baseClickable, int slot) {
        return genericHeadHelper.generateDefaultClickable(
                baseClickable.getStack(),
                slot,
                ClickType.LEFT,
                baseClickable.getClick()
        );
    }

}
