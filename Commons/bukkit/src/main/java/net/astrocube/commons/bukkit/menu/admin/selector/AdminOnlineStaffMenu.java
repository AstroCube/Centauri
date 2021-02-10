package net.astrocube.commons.bukkit.menu.admin.selector;

import me.yushust.message.MessageHandler;
import net.astrocube.api.bukkit.user.staff.OnlineStaffProvider;
import net.astrocube.api.core.virtual.user.User;
import net.astrocube.commons.core.utils.Pagination;
import net.astrocube.commons.core.utils.SimplePagination;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import team.unnamed.gui.abstraction.item.ItemClickable;
import team.unnamed.gui.core.gui.GUIBuilder;
import team.unnamed.gui.core.item.type.ItemBuilder;

import javax.inject.Inject;
import java.util.List;

public class AdminOnlineStaffMenu {

    private @Inject Plugin plugin;
    private @Inject OnlineStaffProvider onlineStaffProvider;
    private @Inject MessageHandler playerMessageHandler;
    private @Inject UserSkullGenerator userSkullGenerator;

    public void createOnlineStaffMenu(Player player, int page) {

        GUIBuilder guiBuilder = GUIBuilder
                .builder(playerMessageHandler.get(player, "admin-panel.online-staff.title"), 6);


        try {
            Pagination<User> userPagination  = new SimplePagination<>(36, onlineStaffProvider.provide());

            int currentIndex = 0;

            for (User user : userPagination.getPage(page)) {
                guiBuilder
                        .addItem(ItemClickable.builder(currentIndex++)
                                .setItemStack(userSkullGenerator.generateSkull(user, player))
                                .build());
            }

            int possibleNextPage = page + 1;
            if (userPagination.pageExists(possibleNextPage)) {

                guiBuilder
                        .addItem(ItemClickable
                                .builder(53)
                                .setItemStack(ItemBuilder
                                        .newBuilder(Material.ARROW)
                                        .setName(playerMessageHandler.get(player, "admin-panel.online-staff.next-name"))
                                        .build())
                                .setAction(event -> {
                                    createOnlineStaffMenu(player, possibleNextPage);
                                    return true;
                                })
                                .build());
            }

            int possiblePreviousPage = page - 1;
            if (userPagination.pageExists(possiblePreviousPage)) {

                guiBuilder
                        .addItem(ItemClickable
                                .builder(52)
                                .setItemStack(ItemBuilder
                                        .newBuilder(Material.REDSTONE)
                                        .setName(playerMessageHandler.get(player, "admin-panel.online-staff.previous-name"))
                                        .build())
                                .setAction(event -> {
                                    createOnlineStaffMenu(player, possiblePreviousPage);
                                    return true;
                                })
                                .build());
            }

            Bukkit.getScheduler()
                    .runTask(plugin, () -> player.openInventory(guiBuilder.build()));

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private List<String> colorizeAndReplace(List<String> list, User user) {
        list.replaceAll(line -> ChatColor.translateAlternateColorCodes('&', line)
                .replace("%last_seen%", user.getSession().getLastSeen())
                .replace("%state%", user.getSession().isOnline() ? "online" : "offline"));
        return list;
    }
}