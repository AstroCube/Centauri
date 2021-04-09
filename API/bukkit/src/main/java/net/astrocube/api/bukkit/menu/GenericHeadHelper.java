package net.astrocube.api.bukkit.menu;

import com.google.inject.Inject;
import me.yushust.message.MessageHandler;
import net.astrocube.api.bukkit.user.display.DisplayMatcher;
import net.astrocube.api.core.virtual.user.User;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import team.unnamed.gui.abstraction.item.ItemClickable;

import java.util.List;
import java.util.function.Consumer;

public class GenericHeadHelper {

    private @Inject MessageHandler messageHandler;
    private @Inject DisplayMatcher displayMatcher;

    public ItemStack backButton(Player player) {
        return generateMetaAndPlace(
                HeadLibrary.BACK_ARROW, messageHandler.get(player, "menus.back"));
    }

    public ItemStack getPreviousHead(Player player, int page) {
        return getPageStack(player, HeadLibrary.LEFT_ARROW_PC, "menus.pagination.previous", page - 1);
    }

    public ItemStack getActual(Player player, int page) {
        return getPageStack(player, HeadLibrary.BOOK_LEFT, "menus.pagination.actual", page);
    }

    public ItemStack getNextHead(Player player, int page) {
        return getPageStack(player, HeadLibrary.RIGHT_ARROW_PC, "menus.pagination.next", page + 1);
    }

    public ItemStack getEmptyHead(Player player, String translation) {
        return generateMetaAndPlace(HeadLibrary.SAD_FACE, messageHandler.get(player, translation));
    }

    public ItemStack generateSkull(Player player, User user, List<String> lore) {
        ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        SkullMeta meta = (SkullMeta) skull.getItemMeta();
        meta.setOwner(user.getUsername());
        meta.setLore(lore);
        meta.setDisplayName(displayMatcher.getDisplay(player, user).getPrefix() + ChatColor.WHITE + " " + user.getDisplay());
        skull.setItemMeta(meta);
        return skull;
    }

    private ItemStack getPageStack(Player player, HeadLibrary head, String translation, int page) {
        return generateMetaAndPlace(
                head,
                messageHandler.replacing(
                        player, translation,
                        "%page%", page
                )
        );
    }

    public ItemClickable generateDefaultClickable(ItemStack stack, int slot, ClickType type, Consumer<Player> playerConsumer) {
        return ItemClickable.builder(slot)
                .setItemStack(stack)
                .setAction(event -> {
                    if (event.getClick() == type) {
                        playerConsumer.accept((Player) event.getWhoClicked());
                    }
                    return true;
                })
        .build();
    }

    public ItemClickable generateDecorator(ItemStack stack, int slot) {
        return ItemClickable.builder(slot)
                .setItemStack(stack)
                .build();
    }

    private ItemStack generateMetaAndPlace(HeadLibrary library, String title) {
        return generateMetaAndPlace(library, title, null);
    }

    public ItemStack generateMetaAndPlace(ItemStack stack, String title, List<String> lore) {
        ItemMeta meta = stack.getItemMeta();
        meta.setDisplayName(title);
        if (lore != null) { meta.setLore(lore); }
        stack.setItemMeta(meta);
        return stack;
    }

    private ItemStack generateMetaAndPlace(HeadLibrary library, String title, List<String> lore) {
        ItemStack stack = MenuUtils.generateHead(library);
        ItemMeta meta = stack.getItemMeta();
        meta.setDisplayName(title);
        if (lore != null) { meta.setLore(lore); }
        stack.setItemMeta(meta);
        return stack;
    }

}
