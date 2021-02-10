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
            Consumer<Player> itemClick,
            Set<ItemStack> items,
            @Nullable Consumer<Player> backClick,
            int page
    );

    Inventory generate(
            Player player,
            String title,
            Consumer<Player> itemClick,
            @Nullable Consumer<Player> backClick,
            Set<ItemStack> items
    );

}
