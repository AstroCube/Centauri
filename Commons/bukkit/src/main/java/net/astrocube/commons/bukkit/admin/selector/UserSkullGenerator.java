package net.astrocube.commons.bukkit.admin.selector;

import me.yushust.message.MessageHandler;
import net.astrocube.api.bukkit.user.display.DisplayMatcher;
import net.astrocube.api.core.virtual.user.User;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import javax.inject.Inject;

public class UserSkullGenerator {

    private @Inject DisplayMatcher displayMatcher;
    private @Inject MessageHandler messageHandler;

    public ItemStack generateSkull(User user, Player player) {

        ItemStack stack = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);

        SkullMeta meta = (SkullMeta) stack.getItemMeta();

        meta.setOwner(user.getSkin());

        meta.setDisplayName(displayMatcher.getDisplay(player, user).getColor() + user.getDisplay());

        meta.setLore(
                messageHandler.replacingMany(
                        player, "admin-panel.online-staff.item-layout.lore",
                        "%connected%", user.getSession().getLastGame()
                )
        );

        stack.setItemMeta(meta);

        return stack;
    }

}
