package net.astrocube.commons.bukkit.game.match;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.astrocube.api.bukkit.game.exception.GameControlException;
import net.astrocube.api.bukkit.game.matchmaking.MatchAssignable;
import net.astrocube.api.bukkit.game.match.MatchAssigner;
import net.astrocube.api.bukkit.virtual.game.match.Match;
import net.astrocube.api.core.redis.Redis;
import redis.clients.jedis.Jedis;

@Singleton
public class CoreMatchAssigner implements MatchAssigner {

    private final Jedis redis;

    @Inject
    public CoreMatchAssigner(Redis redis) {
        this.redis = redis.getRawConnection().getResource();
    }

    @Override
    public void assign(MatchAssignable assignable, Match match) throws GameControlException {

        if (!redis.exists("matchmaking:" + assignable.getResponsible())) {
            throw new GameControlException("No matchmaking request found for this assignation");
        }

        redis.del("matchmaking:" + assignable.getResponsible());
        this.setRecord(assignable.getResponsible(), match.getId());
        assignable.getInvolved().forEach(involved -> this.setRecord(involved, match.getId()));
    }

    private void setRecord(String id, String matchId) {
        this.redis.set("matchAssign:" + id, matchId);
    }

}
