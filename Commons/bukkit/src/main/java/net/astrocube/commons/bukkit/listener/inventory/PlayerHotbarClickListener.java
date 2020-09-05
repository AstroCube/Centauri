package net.astrocube.commons.bukkit.listener.inventory;

import com.google.inject.Inject;
import me.yushust.message.core.MessageProvider;
import net.astrocube.api.bukkit.user.inventory.event.ActionableItemEvent;
import net.astrocube.api.bukkit.user.inventory.nbt.NBTUtils;
import net.astrocube.api.core.service.find.FindService;
import net.astrocube.api.core.virtual.user.User;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nullable;

public class PlayerHotbarClickListener implements Listener {

    private @Inject FindService<User> findService;
    private @Inject MessageProvider<Player> messageProvider;

    @EventHandler
    public void onHotbarClick(PlayerInteractEvent event) {

        @Nullable ItemStack stack = event.getItem();

        if (
                stack != null &&
                stack.getData().getItemType() != Material.AIR &&
                NBTUtils.hasString(stack, "actionable")
        ) {
            findService.find(event.getPlayer().getDatabaseIdentifier()).callback(userResponse -> {
                if (userResponse.isSuccessful() && userResponse.getResponse().isPresent()) {
                    Bukkit.getPluginManager().callEvent(new ActionableItemEvent(
                            event.getPlayer(),
                            userResponse.getResponse().get(),
                            NBTUtils.getString(stack, "actionable"),
                            event.getAction()
                    ));
                    event.setCancelled(true);
                } else {
                    messageProvider.sendMessage(event.getPlayer(), "interaction.actionable-error");
                }
            });
        }
    }

}
