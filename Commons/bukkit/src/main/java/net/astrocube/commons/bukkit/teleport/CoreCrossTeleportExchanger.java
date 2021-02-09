package net.astrocube.commons.bukkit.teleport;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.astrocube.api.bukkit.teleport.CrossTeleportExchanger;
import net.astrocube.api.bukkit.teleport.request.TeleportRequest;
import net.astrocube.api.core.cloud.CloudTeleport;
import net.astrocube.api.core.redis.Redis;
import net.astrocube.api.core.virtual.user.User;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Singleton
public class CoreCrossTeleportExchanger implements CrossTeleportExchanger {

    private final JedisPool jedisPool;
    private final CloudTeleport cloudTeleport;

    @Inject
    public CoreCrossTeleportExchanger(Redis redis, CloudTeleport cloudTeleport) {
        this.jedisPool = redis.getRawConnection();
        this.cloudTeleport = cloudTeleport;
    }

    @Override
    public void schedule(TeleportRequest request) {
        try (Jedis jedis = jedisPool.getResource()) {
            if (request.getRequester().isPresent()) {
                jedis.set("teleportRequest:" + request.getReceiver().getId(),
                        request.getRequester()
                                .get()
                                .getUsername()
                );
            }
        }

        request.getServer().ifPresent(server -> cloudTeleport.teleportToServer(server, request.getReceiver().getUsername()));

    }

    @Override
    public void exchange(User user) {

        try (Jedis jedis = jedisPool.getResource()) {
            Player receiver = Bukkit.getPlayer(user.getUsername());

            if (jedis.exists("teleportRequest:" + user.getId()) && receiver != null) {

                Player requester = Bukkit.getPlayer(jedis.get("teleportRequest:" + user.getId()));

                if (requester != null) {
                    receiver.teleport(requester);
                }
            }

            jedis.del("teleportRequest:" + user.getId());
        }

    }

}
