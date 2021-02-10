package net.astrocube.api.bukkit.menu;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nullable;
import java.util.Set;
import java.util.function.Consumer;

public interface ShapedMenuGenerator {

    Inventory generate(
            Player player,
            String title,
            @Nullable Consumer<Player> backClick,
            Set<BaseClickable> items
    );

    Inventory generate(
            Player player,
            String title,
            @Nullable Consumer<Player> backClick,
            Set<BaseClickable> items,
            int page
    );

    interface BaseClickable {

        Consumer<Player> getClick();

        ItemStack getStack();

    }

}
