package net.astrocube.commons.bukkit.game.match;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.astrocube.api.bukkit.game.event.GameUserDisconnectEvent;
import net.astrocube.api.bukkit.game.exception.GameControlException;
import net.astrocube.api.bukkit.game.match.ActualMatchProvider;
import net.astrocube.api.bukkit.game.match.MatchService;
import net.astrocube.api.bukkit.game.match.UserMatchJoiner;
import net.astrocube.api.bukkit.game.matchmaking.MatchAssignable;
import net.astrocube.api.bukkit.game.match.MatchAssigner;
import net.astrocube.api.bukkit.game.matchmaking.SingleMatchAssignation;
import net.astrocube.api.bukkit.virtual.game.match.Match;
import net.astrocube.api.core.message.Channel;
import net.astrocube.api.core.message.Messenger;
import net.astrocube.api.core.redis.Redis;
import net.astrocube.api.core.service.find.FindService;
import net.astrocube.api.core.virtual.user.User;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.HashMap;
import java.util.Optional;
import java.util.logging.Level;

@Singleton
public class CoreMatchAssigner implements MatchAssigner {

    private final JedisPool jedisPool;
    private final Plugin plugin;
    private final ActualMatchProvider actualMatchProvider;
    private final UserMatchJoiner userMatchJoiner;
    private final FindService<User> findService;
    private final MatchService matchService;
    private final Channel<SingleMatchAssignation> channel;

    @Inject
    public CoreMatchAssigner(Redis redis, ActualMatchProvider actualMatchProvider,
                             Plugin plugin, Messenger jedisMessenger, UserMatchJoiner userMatchJoiner,
                             FindService<User> findService, MatchService matchService) {
        this.jedisPool = redis.getRawConnection();
        this.plugin = plugin;
        this.actualMatchProvider = actualMatchProvider;
        this.channel = jedisMessenger.getChannel(SingleMatchAssignation.class);
        this.userMatchJoiner = userMatchJoiner;
        this.matchService = matchService;
        this.findService = findService;
    }

    @Override
    public void assign(MatchAssignable assignable, Match match) throws Exception {

        try (Jedis jedis = jedisPool.getResource()) {
            if (!jedis.exists("matchmaking:" + assignable.getResponsible())) {
                throw new GameControlException("No matchmaking request found for this assignation");
            }

            jedis.del("matchmaking:" + assignable.getResponsible());

            match.getPending().add(assignable);
            matchService.assignPending(assignable, match.getId());
            this.setRecord(assignable.getResponsible(), match.getId());

            assignable.getInvolved().forEach(involved -> {
                try {
                    this.setRecord(involved, match.getId());
                } catch (Exception e) {
                    plugin.getLogger().log(Level.SEVERE, "There was an error assigning a match user", e);
                }
            });

        }
    }

    @Override
    public void unAssign(Player player) throws Exception {

        Optional<Match> matchOptional = actualMatchProvider.provide(player.getDatabaseIdentifier());

        if (matchOptional.isPresent()) {

            Match match = matchOptional.get();

            if (match.getSpectators().contains(player.getDatabaseIdentifier())) {
                matchService.assignSpectator(player.getDatabaseIdentifier(), match.getId(), false);
            } else if (match.getPending().stream().anyMatch(pending ->
                    pending.getResponsible().equalsIgnoreCase(player.getDatabaseIdentifier()) ||
                            pending.getInvolved().contains(player.getDatabaseIdentifier()))
            ) {
                matchService.unAssignPending(player.getDatabaseIdentifier(), match.getId());
                Bukkit.getPluginManager().callEvent(new GameUserDisconnectEvent(match.getId(), player));
            } else if (match.getTeams().stream().anyMatch(m -> m.getMembers().contains(player.getDatabaseIdentifier()))) {

            }

        }


    }

    private void setRecord(String id, String matchId) throws Exception {
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.set("matchAssign:" + id, matchId);
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

}
