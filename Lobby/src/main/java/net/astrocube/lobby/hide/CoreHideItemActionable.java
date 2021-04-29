package net.astrocube.lobby.hide;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import me.yushust.message.MessageHandler;
import net.astrocube.api.bukkit.lobby.hide.HideItemActionable;
import net.astrocube.api.bukkit.lobby.hide.HideStatusModifier;
import net.astrocube.api.bukkit.translation.mode.AlertModes;
import net.astrocube.api.core.redis.Redis;
import net.astrocube.api.core.service.update.UpdateService;
import net.astrocube.api.core.virtual.user.User;
import net.astrocube.api.core.virtual.user.UserDoc;
import net.astrocube.lobby.hotbar.collection.HideGadgetStack;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import redis.clients.jedis.Jedis;

@Singleton
public class CoreHideItemActionable implements HideItemActionable {

    private @Inject UpdateService<User, UserDoc.Partial> updateService;
    private @Inject HideStatusModifier hideStatusModifier;
    private @Inject Redis redis;
    private @Inject MessageHandler messageHandler;
    private @Inject Plugin plugin;

    @Override
    public void switchHideStatus(User user, Player player) {

        boolean hide = user.getSettings().getGeneralSettings().isHidingPlayers();
        String id = player.getUniqueId().toString();

        try (Jedis jedis = redis.getRawConnection().getResource()) {

            if(jedis.exists("COOL-DOWN:HIDE" + id)) {
                messageHandler.send(player, "no-finished-cool-down");
                return;
            }

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

                    jedis.expire("COOL-DOWN:HIDE" + id, 3);
                } else {
                    messageHandler.sendIn(player, AlertModes.ERROR, "lobby.hiding.error");
                }
            });



        }

    }

}
