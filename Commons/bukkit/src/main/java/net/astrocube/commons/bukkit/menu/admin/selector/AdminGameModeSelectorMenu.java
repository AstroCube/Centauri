package net.astrocube.commons.bukkit.menu.admin.selector;

import me.yushust.message.MessageHandler;
import net.astrocube.api.core.service.query.QueryService;
import net.astrocube.api.core.virtual.gamemode.GameMode;
import net.astrocube.commons.bukkit.menu.admin.selector.item.GameModeItemExtractor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import team.unnamed.gui.core.gui.GUIBuilder;

import javax.inject.Inject;

public class AdminGameModeSelectorMenu {

    @Inject
    private MessageHandler<Player> messageHandler;
    @Inject
    private QueryService<GameMode> gameModeFindService;
    @Inject
    private GameModeItemExtractor gameModeItemExtractor;

    public Inventory createGameModeSelectorMenu(Player player) {

        GUIBuilder guiBuilder = GUIBuilder
                .builder(messageHandler.get(player, ""), 3);

        gameModeFindService
                .getAll()
                .callback(response -> {

                    if (response.isSuccessful() && response.getResponse().isPresent()) {

                        for (GameMode gameMode : response.getResponse().get().getFoundModels()) {

                            guiBuilder.addItem(gameModeItemExtractor.generateGameMode(gameMode, player));
                        }
                    }
                });

        return guiBuilder.build();
    }
}