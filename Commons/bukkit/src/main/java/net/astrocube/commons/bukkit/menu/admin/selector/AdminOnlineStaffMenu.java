package net.astrocube.commons.bukkit.menu.admin.selector;

import me.yushust.message.MessageHandler;
import net.astrocube.api.core.service.query.QueryService;
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
import java.util.Set;

public class AdminOnlineStaffMenu {

    @Inject
    private Plugin plugin;
    @Inject
    private QueryService<User> userQueryService;
    @Inject
    private MessageHandler<Player> playerMessageHandler;

    public void createOnlineStaffMenu(Player player, int page) {

        GUIBuilder guiBuilder = GUIBuilder
                .builder(playerMessageHandler.get(player, "admin-panel.online-staff.title"), 5);

        userQueryService
                .getAll()
                .callback(response -> {

                    if (response.isSuccessful() && response.getResponse().isPresent()) {

                        Set<User> users = response.getResponse().get().getFoundModels();

                        Pagination<User> userPagination = new SimplePagination<>(36, users);

                        int currentIndex = 0;

                        for (User user : userPagination.getPage(page)) {

                            Player staff = Bukkit.getPlayerByIdentifier(user.getId());

                            guiBuilder
                                    .addItem(ItemClickable.builder(currentIndex++)
                                            .setItemStack(ItemBuilder
                                                    .newBuilder(Material.SKULL_ITEM)
                                                    .setName(playerMessageHandler.get(player, "admin-panel.online-staff.item-layout.name")
                                                            .replace("%player_name%", staff.getName()))
                                                    .setLore(colorizeAndReplace(playerMessageHandler.getMany(player, "admin-panel.online-staff.item-layout.lore").getContents(), user))
                                                    .build())
                                            .build());
                        }

                        int possibleNextPage = page + 1;
                        if (userPagination.pageExists(possibleNextPage)) {

                            guiBuilder
                                    .addItem(ItemClickable
                                            .builder(54)
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
                                            .builder(54)
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
                    }
                });
    }

    private List<String> colorizeAndReplace(List<String> list, User user) {
        list.replaceAll(line -> ChatColor.translateAlternateColorCodes('&', line)
                .replace("%last_seen%", user.getSession().getLastSeen())
                .replace("%state%", user.getSession().isOnline() ? "online" : "offline"));
        return list;
    }
}