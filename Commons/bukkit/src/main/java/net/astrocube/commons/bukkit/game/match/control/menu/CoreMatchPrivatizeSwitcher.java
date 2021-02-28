package net.astrocube.commons.bukkit.game.match.control.menu;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import me.yushust.message.MessageHandler;
import net.astrocube.api.bukkit.game.match.ActualMatchCache;
import net.astrocube.api.bukkit.game.match.MatchService;
import net.astrocube.api.bukkit.game.match.control.MatchParticipantsProvider;
import net.astrocube.api.bukkit.game.match.control.menu.MatchPrivatizeSwitcher;
import net.astrocube.api.bukkit.translation.mode.AlertModes;
import net.astrocube.api.bukkit.virtual.game.match.Match;
import org.bukkit.entity.Player;

import java.util.Optional;

@Singleton
public class CoreMatchPrivatizeSwitcher implements MatchPrivatizeSwitcher {

    private @Inject MatchService matchService;
    private @Inject MessageHandler messageHandler;
    private @Inject ActualMatchCache actualMatchCache;

    @Override
    public void switchPrivatization(Player player) throws Exception {

        Optional<Match> matchOptional = actualMatchCache.get(player.getDatabaseIdentifier());

        if (!matchOptional.isPresent()) {
            messageHandler.sendIn(player, AlertModes.ERROR, "game.admin.not-active");
            return;
        }

        Match match = matchOptional.get();

        matchService.privatizeMatch(player.getDatabaseIdentifier(), match.getId());

        String translation = !match.isPrivate() ?
                "game.admin.lobby.privatizing.enabled" : "game.admin.lobby.privatizing.disabled";

        MatchParticipantsProvider.getWaitingIds(match).forEach(
                p -> messageHandler.sendIn(p, AlertModes.INFO, translation)
        );

    }

}
