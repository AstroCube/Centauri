package net.astrocube.lobby.selector.lobby;

import com.google.inject.Inject;
import me.yushust.message.MessageHandler;
import net.astrocube.api.bukkit.lobby.gamemode.LobbyModeProvider;
import net.astrocube.api.bukkit.lobby.selector.lobby.LobbyCloudWrapperGenerator;
import net.astrocube.api.bukkit.lobby.selector.lobby.LobbyIconExtractor;
import net.astrocube.api.bukkit.lobby.selector.lobby.LobbySelectorDisplay;
import net.astrocube.api.bukkit.lobby.selector.lobby.LobbySelectorWrapper;
import net.astrocube.api.core.virtual.gamemode.GameMode;
import org.bukkit.entity.Player;
import team.unnamed.gui.abstraction.item.ItemClickable;
import team.unnamed.gui.core.gui.GUIBuilder;
import java.util.List;
import java.util.Optional;

public class CoreLobbySelectorDisplay implements LobbySelectorDisplay {

    private @Inject LobbyModeProvider lobbyModeProvider;
    private @Inject MessageHandler<Player> messageHandler;
    private @Inject LobbyCloudWrapperGenerator lobbyCloudWrapperGenerator;
    private @Inject LobbyIconExtractor lobbyIconExtractor;

    @Override
    public void openDisplay(Player player, int page) {

        Optional<GameMode> gameMode = lobbyModeProvider.getRegisteredMode();

        if (!gameMode.isPresent()) {
            messageHandler.send(player, "lobby.lobby-selector.error.not-detected");
            return;
        }

        GUIBuilder menuBuilder = GUIBuilder.builder(
                messageHandler.get(player, "lobby.lobby-selector.gadget-title"),
                1
        );
        List<LobbySelectorWrapper> wrappers = lobbyCloudWrapperGenerator.getGameModeLobbies(gameMode.get());

        int externalCount = 0;
        for (int i = 10; i < wrappers.size() + 10; i++) {
            if (i != 17 && i != 18 && i != 26 && i != 27) {
                menuBuilder.addItem(
                        lobbyIconExtractor.getLobbyIcon(wrappers.get(externalCount), player, externalCount)
                );
                externalCount++;
            }
        }

        player.openInventory(menuBuilder.build());
    }
}