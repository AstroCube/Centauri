package net.astrocube.commons.bukkit.game.match;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.astrocube.api.bukkit.game.exception.GameControlException;
import net.astrocube.api.bukkit.game.match.UserMatchJoiner;
import net.astrocube.api.bukkit.game.matchmaking.MatchAssignable;
import net.astrocube.api.bukkit.game.match.MatchAssigner;
import net.astrocube.api.bukkit.game.matchmaking.SingleMatchAssignation;
import net.astrocube.api.bukkit.virtual.game.match.Match;
import net.astrocube.api.bukkit.virtual.game.match.MatchDoc;
import net.astrocube.api.core.message.Channel;
import net.astrocube.api.core.message.ChannelBind;
import net.astrocube.api.core.message.ChannelBinder;
import net.astrocube.api.core.message.Messenger;
import net.astrocube.api.core.redis.Redis;
import net.astrocube.api.core.service.find.FindService;
import net.astrocube.api.core.service.update.UpdateService;
import net.astrocube.api.core.virtual.user.User;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.logging.Level;

@Singleton
public class CoreMatchAssigner implements MatchAssigner {

    private final Jedis redis;
    private final Plugin plugin;
    private final UpdateService<Match, MatchDoc.Partial> updateService;
    private final UserMatchJoiner userMatchJoiner;
    private final FindService<User> findService;
    private final Channel<SingleMatchAssignation> channel;

    @Inject
    public CoreMatchAssigner(Redis redis, UpdateService<Match, MatchDoc.Partial> updateService,
                             Plugin plugin, Messenger jedisMessenger, UserMatchJoiner userMatchJoiner, FindService<User> findService) {
        this.redis = redis.getRawConnection().getResource();
        this.updateService = updateService;
        this.plugin = plugin;
        this.channel = jedisMessenger.getChannel(SingleMatchAssignation.class);
        this.userMatchJoiner = userMatchJoiner;
        this.findService = findService;
    }

    @Override
    public void assign(MatchAssignable assignable, Match match) throws Exception {

        if (!redis.exists("matchmaking:" + assignable.getResponsible())) {
            throw new GameControlException("No matchmaking request found for this assignation");
        }

        redis.del("matchmaking:" + assignable.getResponsible());

        match.getPending().add(assignable);
        this.setRecord(assignable.getResponsible(), match.getId());

        this.updateService.update(match).callback(updateResponse -> {

            if (!updateResponse.isSuccessful() || !updateResponse.getResponse().isPresent()) {
                Bukkit.getLogger().warning("There was an error while updating the match assignation.");
            }

            assignable.getInvolved().forEach(involved -> {
                try {
                    this.setRecord(involved, match.getId());
                } catch (Exception e) {
                    plugin.getLogger().log(Level.SEVERE, "There was an error assigning a match user", e);
                }
            });

        });
    }

    private void setRecord(String id, String matchId) throws Exception {
        this.redis.set("matchAssign:" + id, matchId);
        if (plugin.getConfig().getBoolean("server.sandbox")) {

            User user = findService.findSync(id);
            Player player = Bukkit.getPlayer(user.getUsername());

            userMatchJoiner.processJoin(user, player);

        } else {
            channel.sendMessage(new SingleMatchAssignation() {
                @Override
                public String getUser() {
                    return id;
                }

                @Override
                public String getMatch() {
                    return matchId;
                }
            }, new HashMap<>());
        }

    }

}
