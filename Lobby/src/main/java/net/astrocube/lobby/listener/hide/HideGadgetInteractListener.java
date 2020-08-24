package net.astrocube.lobby.listener.hide;

import com.google.inject.Inject;
import me.yushust.message.core.MessageProvider;
import net.astrocube.api.bukkit.user.inventory.event.ActionableItemEvent;
import net.astrocube.api.core.service.find.FindService;
import net.astrocube.api.core.virtual.user.User;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;

public class HideGadgetInteractListener implements Listener {

    private @Inject FindService<User> findService;
    private @Inject MessageProvider<Player> messageProvider;

    @EventHandler
    public void onGadgetInteract(ActionableItemEvent event) {
        if (
                event.getAction().equalsIgnoreCase("hide_gadget") &&
                (event.getClick() == Action.RIGHT_CLICK_AIR || event.getClick() == Action.RIGHT_CLICK_BLOCK)
        ) {
            findService.find(event.getPlayer().getDatabaseIdentifier()).callback(userResponse -> {
                if (userResponse.isSuccessful() && userResponse.getResponse().isPresent()) {

                } else {
                    messageProvider.sendMessage(event.getPlayer(), "lobby.hiding.error");
                }
            });
        }
    }
}
