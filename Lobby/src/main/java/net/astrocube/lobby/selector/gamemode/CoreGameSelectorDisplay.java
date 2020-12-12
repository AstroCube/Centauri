package net.astrocube.lobby.selector.gamemode;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import me.yushust.message.MessageHandler;
import net.astrocube.api.bukkit.lobby.selector.gamemode.GameItemExtractor;
import net.astrocube.api.bukkit.lobby.selector.gamemode.GameSelectorDisplay;
import net.astrocube.api.core.service.query.QueryService;
import net.astrocube.api.core.virtual.gamemode.GameMode;
import net.astrocube.api.core.virtual.user.User;
import org.bukkit.entity.Player;
import team.unnamed.gui.abstraction.item.ItemClickable;
import team.unnamed.gui.core.gui.type.GUIBuilder;

@Singleton
public class CoreGameSelectorDisplay implements GameSelectorDisplay {

    private @Inject MessageHandler<Player> messageHandler;
    private @Inject QueryService<GameMode> queryService;
    private @Inject GameItemExtractor gameItemExtractor;

    @Override
    public void openDisplay(User user, Player player) {

        queryService.getAll().callback(modesResponse -> {
            if (modesResponse.isSuccessful() && modesResponse.getResponse().isPresent()) {

                GUIBuilder<ItemClickable> menuBuilder = GUIBuilder.builder(
                        messageHandler.get(player, "lobby.gameSelector.title"),
                        1
                );

                for (GameMode gameModeDoc : modesResponse.getResponse().get().getFoundModels()) {
                    menuBuilder.addItem(gameItemExtractor.generate(gameModeDoc, player));
                }

                player.openInventory(menuBuilder.build());

            } else {
                player.sendMessage(messageHandler.get(player, "lobby.gameSelector.error"));
            }
        });

    }

}
