package net.astrocube.commons.bukkit.menu;

import net.astrocube.api.bukkit.user.profile.AbstractGameProfile;
import net.astrocube.commons.bukkit.user.profile.CoreGameProfile;
import net.astrocube.commons.bukkit.user.profile.CoreProperty;
import net.astrocube.commons.bukkit.user.profile.MojangProfileGeneratorUtil;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.UUID;

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

    public static ItemStack generateHead(HeadLibrary library) {

        ItemStack head = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        Field profileField;
        SkullMeta meta = (SkullMeta) head.getItemMeta();

        AbstractGameProfile profile = new CoreGameProfile(UUID.randomUUID(), "head");
        profile.getProperties().add(new CoreProperty("textures", library.getBase64()));

        try {
            profileField = meta.getClass().getDeclaredField("profile");
            profileField.setAccessible(true);
            profileField.set(meta, MojangProfileGeneratorUtil.generateProfile(profile));
        } catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
            e.printStackTrace();
        }

        head.setItemMeta(meta);
        return head;
    }

}
