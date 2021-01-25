package net.astrocube.commons.bukkit.game.match;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.astrocube.api.bukkit.game.exception.GameControlException;
import net.astrocube.api.bukkit.game.match.ActualMatchCache;
import net.astrocube.api.bukkit.game.match.ActualMatchProvider;
import net.astrocube.api.bukkit.virtual.game.match.Match;
import net.astrocube.api.core.redis.Redis;
import org.bukkit.plugin.Plugin;
import redis.clients.jedis.Jedis;

import java.util.Optional;
import java.util.logging.Level;

@Singleton
public class CoreActualMatchCache implements ActualMatchCache {

    private @Inject ActualMatchProvider actualMatchProvider;
    private @Inject Plugin plugin;
    private @Inject ObjectMapper mapper;
    private @Inject Redis redis;

    @Override
    public Optional<Match> get(String id) throws Exception {

        try (Jedis jedis = redis.getRawConnection().getResource()) {

            if (jedis.exists("actualMatch:" + id)) {
                return Optional.of(mapper.readValue(
                        jedis.get("actualMatch:" + id),
                        Match.class
                ));
            } else {

                Optional<Match> match = actualMatchProvider.provide(id);

                if (match.isPresent()) {
                    jedis.set("actualMatch:" + id, mapper.writeValueAsString(match.get()));
                    jedis.expire("actualMatch:" + id, 120);
                    return match;
                }

            }

        } catch (Exception e) {
            throw new GameControlException("Error while opening redis cache connection");
        }

        return Optional.empty();
    }

    @Override
    public void remove(String id) {
        try (Jedis jedis = redis.getRawConnection().getResource()) {
            jedis.del("actualMatch:" + id);
        } catch (Exception e) {
            plugin.getLogger().log(Level.WARNING, "Error while removing match from cache", e);
        }
    }

}
