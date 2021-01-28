package net.astrocube.commons.bukkit.menu.admin.selector;

import me.yushust.message.MessageHandler;
import net.astrocube.api.core.service.query.QueryService;
import net.astrocube.api.core.virtual.gamemode.GameMode;
import net.astrocube.commons.bukkit.menu.admin.selector.item.GameModeItemExtractor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import team.unnamed.gui.core.gui.GUIBuilder;

import javax.inject.Inject;

public class AdminGameModeSelectorMenu {

    @Inject
    private MessageHandler<Player> messageHandler;
    @Inject
    private QueryService<GameMode> gameModeQueryService;
    @Inject
    private GameModeItemExtractor gameModeItemExtractor;
    @Inject
    private Plugin plugin;

    public void createGameModeSelectorMenu(Player player) {

        GUIBuilder guiBuilder = GUIBuilder
                .builder(messageHandler.get(player, "lobby.gameSelector.title"), 3);

        gameModeQueryService
                .getAll()
                .callback(response -> {

                    if (response.isSuccessful() && response.getResponse().isPresent()) {

                        for (GameMode gameMode : response.getResponse().get().getFoundModels()) {

                            guiBuilder.addItem(gameModeItemExtractor.generateGameMode(gameMode, player));
                        }

                        Bukkit.getScheduler().runTask(plugin, () -> player.openInventory(guiBuilder.build()));
                    }
                });
    }
}