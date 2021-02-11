package net.astrocube.commons.bukkit.admin.selector;

import me.yushust.message.MessageHandler;
import net.astrocube.api.core.service.query.QueryService;
import net.astrocube.api.core.virtual.gamemode.GameMode;
import net.astrocube.commons.bukkit.admin.selector.item.GameModeItemExtractor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import team.unnamed.gui.core.gui.GUIBuilder;

import javax.inject.Inject;

public class AdminGameModeSelectorMenu {

    private @Inject MessageHandler messageHandler;
    private @Inject QueryService<GameMode> gameModeQueryService;
    private @Inject GameModeItemExtractor gameModeItemExtractor;
    private @Inject Plugin plugin;

    public void createGameModeSelectorMenu(Player player) {

        GUIBuilder guiBuilder = GUIBuilder
                .builder(messageHandler.get(player, "admin-panel.gamemode.title"), 1);

        gameModeQueryService
                .getAll()
                .callback(response -> {

                    if (response.isSuccessful() && response.getResponse().isPresent()) {

                        for (GameMode gameMode : response.getResponse().get().getFoundModels()) {

                            if (!plugin.getConfig().getStringList("admin.teleport.disabled").contains(gameMode.getId())) {
                                guiBuilder.addItem(gameModeItemExtractor.generateGameMode(gameMode, player));
                            }

                        }

                        Bukkit.getScheduler().runTask(plugin, () -> player.openInventory(guiBuilder.build()));
                    }
                });
    }
}