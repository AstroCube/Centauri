package net.astrocube.commons.bukkit.game.match;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.astrocube.api.bukkit.game.event.game.GameUserJoinEvent;
import net.astrocube.api.bukkit.game.exception.GameControlException;
import net.astrocube.api.bukkit.game.match.UserMatchJoiner;
import net.astrocube.api.bukkit.virtual.game.match.Match;
import net.astrocube.api.core.redis.Redis;
import net.astrocube.api.core.service.find.FindService;
import net.astrocube.api.core.virtual.user.User;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Singleton
public class CoreUserMatchJoiner implements UserMatchJoiner {

    private final JedisPool jedisPool;
    private final FindService<Match> findService;

    @Inject
    public CoreUserMatchJoiner(Redis redis, FindService<Match> findService) {
        this.jedisPool = redis.getRawConnection();
        this.findService = findService;
    }

    @Override
    public void processJoin(User user, Player player) throws Exception {

        try (Jedis jedis = jedisPool.getResource()) {
            if (!jedis.exists("matchAssign:" + user.getId())) {
                throw new GameControlException("There was not found any match assignation for this user");
            }

            Match match = findService.findSync(jedis.get("matchAssign:" + user.getId()));
            Origin origin = UserMatchJoiner.checkOrigin(user.getId(), match);
            jedis.del("matchAssign:" + user.getId());

            Bukkit.getPluginManager().callEvent(new GameUserJoinEvent(match.getId(), player, origin));
        }

    }

}
