package net.astrocube.commons.bukkit.authentication.server;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import me.yushust.message.MessageHandler;
import net.astrocube.api.bukkit.authentication.server.AuthenticationCooldown;
import net.astrocube.api.bukkit.authentication.server.CooldownKick;
import net.astrocube.api.core.redis.Redis;
import net.astrocube.api.core.virtual.user.User;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;


@Singleton
public class CoreCooldownKick implements CooldownKick {

    private final AuthenticationCooldown authenticationCooldown;
    private final MessageHandler<Player> messageHandler;
    private final Plugin plugin;
    private final JedisPool jedisPool;

    @Inject
    public CoreCooldownKick(
            AuthenticationCooldown authenticationCooldown,
            MessageHandler<Player> messageHandler,
            Plugin plugin,
            Redis redis
    ) {
        this.authenticationCooldown = authenticationCooldown;
        this.messageHandler = messageHandler;
        this.plugin = plugin;
        this.jedisPool = redis.getRawConnection();
    }

    @Override
    public void checkAndKick(User user, Player player) {
        if (authenticationCooldown.hasCooldown(user.getId())) {
            Bukkit.getScheduler().runTask(plugin, () -> player.kickPlayer(
                    ChatColor.RED + messageHandler.get(player, "authentication.attempts")
            ));
        }
    }

    @Override
    public void addTry(User user) {
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.set("authAttempts:" + user.getId(), getTries(user) + 1 + "");
        }
    }

    @Override
    public void clearTries(User user) {
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.del("authAttempts:" + user.getId());
        }
    }

    @Override
    public int getTries(User user) {
        try (Jedis jedis = jedisPool.getResource()) {
            return  jedis.exists("authAttempts:" + user.getId()) ?
                    Integer.parseInt(jedis.get("authAttempts:" + user.getId())) : 0;
        }
    }

}
