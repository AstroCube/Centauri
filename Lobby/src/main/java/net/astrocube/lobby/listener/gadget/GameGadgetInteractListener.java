package net.astrocube.lobby.listener.gadget;

import com.google.inject.Inject;
import net.astrocube.api.bukkit.lobby.selector.gamemode.GameSelectorDisplay;
import net.astrocube.api.bukkit.user.inventory.event.ActionableItemEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;

public class GameGadgetInteractListener implements Listener {

    private @Inject GameSelectorDisplay gameSelectorDisplay;

    @EventHandler
    public void onGameGadgetInteract(ActionableItemEvent event) {
        if (
                event.getAction().equalsIgnoreCase("game_menu") &&
                        (event.getClick() == Action.RIGHT_CLICK_AIR || event.getClick() == Action.RIGHT_CLICK_BLOCK)
        ) {
            gameSelectorDisplay.openDisplay(event.getUser(), event.getPlayer());
        }
    }
}
