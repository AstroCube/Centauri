package net.astrocube.commons.bukkit.game;

import com.google.inject.Inject;
import lombok.AllArgsConstructor;
import lombok.Getter;
import net.astrocube.api.bukkit.game.matchmaking.MatchmakingRequest;
import net.astrocube.api.core.service.find.FindService;
import net.astrocube.api.core.virtual.gamemode.GameMode;
import net.astrocube.api.core.virtual.gamemode.SubGameMode;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Level;

public class GameControlHelper {

    private @Inject FindService<GameMode> findService;
    private @Inject Plugin plugin;

    public Optional<ModeCompound> getService(String gameMode, String subGameMode) throws Exception {

        GameMode mode = findService.findSync(gameMode);

        if (mode.getSubTypes() == null) {
            plugin.getLogger().log(Level.SEVERE, "The requested GameMode does not have any SubMode");
            return Optional.empty();
        }

        Optional<SubGameMode> subMode = mode.getSubTypes().stream()
                .filter(g -> g.getId().equalsIgnoreCase(subGameMode))
                .findFirst();

        if (!subMode.isPresent()) {
            plugin.getLogger().log(Level.SEVERE, "The requested GameMode was not found");
            return Optional.empty();
        }

        return Optional.of(new ModeCompound(mode, subMode.get()));
    }

    public static Set<Player> getPlayersFromRequest(MatchmakingRequest request) {
        Set<Player> players = new HashSet<>();

        Player requester = Bukkit.getPlayerByIdentifier(request.getRequesters().getResponsible());

        if (requester != null) {
            players.add(requester);
        }

        request.getRequesters().getInvolved().forEach(user -> {
            Player involved = Bukkit.getPlayerByIdentifier(user);

            if (involved != null) {
                players.add(involved);
            }

        });

        return players;
    }

    @Getter
    @AllArgsConstructor
    public static class ModeCompound {
        private final GameMode gameMode;
        private final SubGameMode subGameMode;
    }

}
