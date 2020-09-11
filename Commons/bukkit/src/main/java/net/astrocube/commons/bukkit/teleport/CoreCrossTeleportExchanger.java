package net.astrocube.commons.bukkit.teleport;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.astrocube.api.bukkit.teleport.CrossTeleportExchanger;
import net.astrocube.api.bukkit.teleport.request.TeleportRequest;
import net.astrocube.api.core.redis.Redis;
import net.astrocube.api.core.virtual.user.User;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import redis.clients.jedis.Jedis;

@Singleton
public class CoreCrossTeleportExchanger implements CrossTeleportExchanger {

    private final Jedis jedis;

    @Inject
    public CoreCrossTeleportExchanger(Redis redis) {
        this.jedis = redis.getRawConnection().getResource();
    }

    @Override
    public void schedule(TeleportRequest request) {
        //TODO: Teleport player to corresponding server

        if (request.getRequester().isPresent()) {
            jedis.set("request:" + request.getReceiver().getId(),
                    request.getRequester()
                            .get()
                            .getUsername()
            );
        }

    }

    @Override
    public void exchange(User user) {

        Player receiver = Bukkit.getPlayer(user.getUsername());
        Player requester = Bukkit.getPlayer(jedis.get("request:" + user.getId()));

        if (jedis.exists("request:" + user.getId()) && requester != null && receiver != null) {
            receiver.teleport(requester);
        }

    }

}
