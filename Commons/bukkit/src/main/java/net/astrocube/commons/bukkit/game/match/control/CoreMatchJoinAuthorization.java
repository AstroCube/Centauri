package net.astrocube.commons.bukkit.game.match.control;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.inject.Singleton;
import net.astrocube.api.bukkit.game.exception.GameControlException;
import net.astrocube.api.bukkit.game.match.control.MatchJoinAuthorization;
import net.astrocube.api.bukkit.virtual.game.match.Match;
import net.astrocube.api.core.service.find.FindService;

import java.time.Duration;

@Singleton
public class CoreMatchJoinAuthorization implements MatchJoinAuthorization {

    private final Cache<String, String> authorizationCache;
    private final FindService<Match> findService;

    public CoreMatchJoinAuthorization(FindService<Match> findService) {
        this.findService = findService;
        this.authorizationCache = CacheBuilder
                .newBuilder()
                .expireAfterWrite(Duration.ofSeconds(30))
                .build();
    }

    @Override
    public void generateMatchAuthorization(String match, String user) {
        authorizationCache.put("", "");
    }


    @Override
    public Match processAuthorization(String user) throws Exception {

        String authorization = authorizationCache.getIfPresent(user);

        if (authorization == null) { throw new GameControlException("Authorized match for this user not found"); }

        authorizationCache.invalidate(authorization);

        return this.findService.findSync(authorization);
    }

}
