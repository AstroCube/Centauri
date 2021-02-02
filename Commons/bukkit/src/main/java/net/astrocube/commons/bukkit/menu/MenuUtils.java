package net.astrocube.commons.bukkit.menu;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class MenuUtils {

    public static int[] slots = new int[]{9, 17, 18, 26, 27, 35, 36, 44};

    public static boolean isMarkedSlot(int slot) {
        return (slot >= 0 && slot < 9) || (slot > 44 && slot < 54) || Arrays.stream(slots).anyMatch(s -> s == slot);
    }

    public static ItemStack generateStainedPane() {
        ItemStack stack = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 7);
        ItemMeta meta = stack.getItemMeta();
        meta.setDisplayName(" ");
        stack.setItemMeta(meta);
        return stack;
    }

}
