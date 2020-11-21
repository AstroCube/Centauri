package net.astrocube.commons.bukkit.game.match;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.astrocube.api.bukkit.game.event.GameUserJoinEvent;
import net.astrocube.api.bukkit.game.exception.GameControlException;
import net.astrocube.api.bukkit.game.match.UserMatchJoiner;
import net.astrocube.api.bukkit.virtual.game.match.Match;
import net.astrocube.api.core.redis.Redis;
import net.astrocube.api.core.service.find.FindService;
import net.astrocube.api.core.virtual.user.User;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import redis.clients.jedis.Jedis;

@Singleton
public class CoreUserMatchJoiner implements UserMatchJoiner {

    private final Jedis redis;
    private final FindService<Match> findService;

    @Inject
    public CoreUserMatchJoiner(Redis redis, FindService<Match> findService) {
        this.redis = redis.getRawConnection().getResource();
        this.findService = findService;
    }

    @Override
    public void processJoin(User user, Player player) throws Exception {

        if (!redis.exists("matchAssign:" + user.getId())) {
            throw new GameControlException("There was not found any match assignation for this user");
        }

        redis.del("matchAssign:" + user.getId());

        Match match = findService.findSync(user.getId());
        UserMatchJoiner.Status status = Status.WAITING;

        if (match.getSpectators().contains(user.getId())) {
            status = Status.SPECTATING;
        } else if (match.getTeams().stream().noneMatch(m -> m.getMembers().contains(user.getId()))) {
            status = Status.PLAYING;
        } else if (match.getPending().stream().anyMatch(m -> m.getInvolved().contains(user.getId()))) {
            throw new GameControlException("There was no assignation found for this user");
        }

        Bukkit.getPluginManager().callEvent(new GameUserJoinEvent(match.getId(), player, status));

    }

}
