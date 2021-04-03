package net.astrocube.commons.bukkit.authentication.gateway.premium;

import com.google.inject.Inject;
import net.astrocube.api.bukkit.authentication.AuthenticationGateway;
import net.astrocube.api.bukkit.authentication.event.AuthenticationSuccessEvent;
import net.astrocube.api.core.redis.Redis;
import net.astrocube.api.core.virtual.user.User;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import redis.clients.jedis.Jedis;

public class PremiumGateway implements AuthenticationGateway {

    private @Inject Redis redis;

    @Override
    public void startProcessing(User user) {

        Player player = Bukkit.getPlayerByIdentifier(user.getId());

        if (player != null) {

            try (Jedis jedis = redis.getRawConnection().getResource()) {

                if (!jedis.exists("premium:" + user.getId())) {
                    player.sendMessage(ChatColor.RED + "Unable to verify your premium session. If problem persists contact an administrator");
                    return;
                }

                Bukkit.getPluginManager().callEvent(
                        new AuthenticationSuccessEvent(
                                this,
                                Bukkit.getPlayerByIdentifier(user.getId())
                        )
                );

            }

        }

    }

    @Override
    public String getName() {
        return "Premium";
    }


}
