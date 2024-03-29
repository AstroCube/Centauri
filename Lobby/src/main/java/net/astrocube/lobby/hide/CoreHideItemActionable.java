package net.astrocube.lobby.hide;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import me.yushust.message.MessageHandler;
import net.astrocube.api.bukkit.lobby.hide.HideItemActionable;
import net.astrocube.api.bukkit.lobby.hide.HideStatusModifier;
import net.astrocube.api.core.service.update.UpdateService;
import net.astrocube.api.core.virtual.user.User;
import net.astrocube.api.core.virtual.user.UserDoc;
import net.astrocube.lobby.hotbar.collection.HideGadgetStack;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

@Singleton
public class CoreHideItemActionable implements HideItemActionable {

    private @Inject UpdateService<User, UserDoc.Partial> updateService;
    private @Inject HideStatusModifier hideStatusModifier;
    private @Inject MessageHandler<Player> messageHandler;
    private @Inject Plugin plugin;

    @Override
    public void switchHideStatus(User user, Player player) {

        boolean hide = user.getSettings().getGeneralSettings().isHidingPlayers();
        user.getSettings().getGeneralSettings().setHidingPlayers(!hide);
        updateService.update(user).callback(userUpdate -> {
            if (userUpdate.isSuccessful() && userUpdate.getResponse().isPresent()) {

                String translateAlert = "lobby.hiding.enabled";

                if (!hide) {
                    hideStatusModifier.globalApply(user);
                } else {
                    Bukkit.getScheduler().runTask(plugin, () -> hideStatusModifier.restore(user));
                    translateAlert = "lobby.hiding.disabled";
                }

                player.getInventory().setItem(7, HideGadgetStack.get(messageHandler, player, !hide));
                messageHandler.send(player, translateAlert);

            } else {
                messageHandler.send(player, "lobby.hiding.error");
            }
        });

    }

}
