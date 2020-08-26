package net.astrocube.lobby.listener.gadget;

import com.google.inject.Inject;
import net.astrocube.api.bukkit.lobby.hide.HideItemActionable;
import net.astrocube.api.bukkit.user.inventory.event.ActionableItemEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;

public class HideGadgetInteractListener implements Listener {

    private @Inject HideItemActionable hideItemActionable;

    @EventHandler
    public void onGadgetInteract(ActionableItemEvent event) {
        if (
                event.getAction().equalsIgnoreCase("hide_gadget") &&
                (event.getClick() == Action.RIGHT_CLICK_AIR || event.getClick() == Action.RIGHT_CLICK_BLOCK)
        ) {
            hideItemActionable.switchHideStatus(event.getUser(), event.getPlayer());
        }
    }
}
