package net.astrocube.lobby.hotbar;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import me.yushust.message.core.MessageProvider;
import net.astrocube.api.bukkit.lobby.hotbar.LobbyHotbarProvider;
import net.astrocube.api.core.virtual.user.User;
import net.astrocube.lobby.hotbar.collection.GameGadgetStack;
import net.astrocube.lobby.hotbar.collection.HideGadgetStack;
import net.astrocube.lobby.hotbar.collection.LobbyGadgetStack;
import org.bukkit.entity.Player;

@Singleton
public class CoreLobbyHotbarProvider implements LobbyHotbarProvider {

    private @Inject MessageProvider<Player> messageProvider;

    @Override
    public void setup(User user, Player player) {

        player.getInventory().setItem(0, GameGadgetStack.get(
                messageProvider,
                player
        ));

        player.getInventory().setItem(7, HideGadgetStack.get(
                messageProvider,
                player,
                user.getSettings().getGeneralSettings().isHidingPlayers()
        ));

        player.getInventory().setItem(8, LobbyGadgetStack.get(
                messageProvider,
                player
        ));
    }

}
