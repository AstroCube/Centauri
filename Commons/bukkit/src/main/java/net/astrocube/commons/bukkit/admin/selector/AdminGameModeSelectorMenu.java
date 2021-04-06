package net.astrocube.commons.bukkit.admin.selector;

import me.yushust.message.MessageHandler;
import net.astrocube.api.bukkit.menu.GenericHeadHelper;
import net.astrocube.api.core.service.query.QueryService;
import net.astrocube.api.core.virtual.gamemode.GameMode;
import net.astrocube.commons.bukkit.admin.AdminPanelMenu;
import net.astrocube.commons.bukkit.admin.selector.item.GameModeItemExtractor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.plugin.Plugin;
import team.unnamed.gui.abstraction.item.ItemClickable;
import team.unnamed.gui.abstraction.item.ItemClickableBuilder;
import team.unnamed.gui.core.gui.GUIBuilder;

import javax.inject.Inject;

public class AdminGameModeSelectorMenu {

    private @Inject MessageHandler messageHandler;
    private @Inject QueryService<GameMode> gameModeQueryService;
    private @Inject GameModeItemExtractor gameModeItemExtractor;
    private @Inject GenericHeadHelper genericHeadHelper;
    private @Inject AdminPanelMenu adminPanelMenu;
    private @Inject Plugin plugin;

    public void createGameModeSelectorMenu(Player player) {

        GUIBuilder guiBuilder = GUIBuilder
                .builder(messageHandler.get(player, "admin-panel.gamemode.title"), 1);

        gameModeQueryService
                .getAll()
                .callback(response -> {

                    response.ifSuccessful(modes -> {

                        guiBuilder.addItem(
                                ItemClickable.builder(0).setItemStack(genericHeadHelper.backButton(player))
                                        .setAction(action -> {

                                            if (action.getClick() == ClickType.LEFT) {
                                                Bukkit.getScheduler().runTask(plugin, () -> adminPanelMenu.createAdminPanel(player));
                                            }

                                            return true;
                                        })
                                .build()
                        );

                        for (GameMode gameMode : modes.getFoundModels()) {

                            if (!plugin.getConfig()
                                    .getStringList("admin.teleport.disabled").contains(gameMode.getId())
                            ) {
                                guiBuilder.addItem(gameModeItemExtractor.generateGameMode(gameMode, player));
                            }

                        }

                        Bukkit.getScheduler().runTask(plugin, () -> player.openInventory(guiBuilder.build()));
                    });
                });
    }
}