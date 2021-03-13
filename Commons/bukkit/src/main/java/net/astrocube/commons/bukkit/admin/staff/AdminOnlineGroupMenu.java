package net.astrocube.commons.bukkit.admin.staff;

import com.google.inject.Inject;
import me.yushust.message.MessageHandler;
import net.astrocube.api.bukkit.menu.ShapedMenuGenerator;
import net.astrocube.api.bukkit.user.staff.OnlineStaffProvider;
import net.astrocube.api.core.virtual.user.User;
import net.astrocube.api.bukkit.menu.GenericHeadHelper;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

public class AdminOnlineGroupMenu {

    private @Inject ShapedMenuGenerator shapedMenuGenerator;
    private @Inject AdminOnlineStaffMenu adminOnlineStaffMenu;
    private @Inject OnlineStaffProvider onlineStaffProvider;
    private @Inject MessageHandler messageHandler;
    private @Inject GenericHeadHelper genericHeadHelper;

    public Inventory createOnlineStaffMenu(Player player, String id) throws Exception {
        return shapedMenuGenerator.generate(
                player,
                messageHandler.get(player, "admin-panel.online-staff.title"),
                (p) -> {
                    try {
                        p.openInventory(adminOnlineStaffMenu.createOnlineStaffMenu(player));
                    } catch (Exception e) {
                        messageHandler.send(player, "admin-panel.online-staff.error");
                        player.closeInventory();
                    }
                },
                getCategories(player, id)
        );

    }

    private Set<ShapedMenuGenerator.BaseClickable> getCategories(Player player, String id) throws Exception {

        Set<ShapedMenuGenerator.BaseClickable> clickables = new HashSet<>();

        for (User user : onlineStaffProvider.getFromGroup(id)) {

            ItemStack stack = genericHeadHelper.generateSkull(
                    player,
                    user,
                    messageHandler.replacingMany(
                            player, "admin-panel.online-staff.item-layout.lore",
                            "%connected%", user.getSession().getLastSeen()
                    )
            );

            clickables.add(new ShapedMenuGenerator.BaseClickable() {
                @Override
                public Consumer<Player> getClick() {
                    return (p) -> {};
                }

                @Override
                public ItemStack getStack() {
                    return stack;
                }
            });
        }

        return clickables;

    }

}