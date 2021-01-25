package net.astrocube.commons.bukkit.game.match;

import me.yushust.message.MessageHandler;
import net.astrocube.api.bukkit.game.match.ActualMatchProvider;
import net.astrocube.api.bukkit.game.match.MatchRejoiner;
import net.astrocube.api.bukkit.game.match.UserMatchJoiner;
import net.astrocube.api.bukkit.translation.mode.AlertMode;
import net.astrocube.api.bukkit.virtual.game.match.Match;
import net.astrocube.api.core.service.find.FindService;
import net.astrocube.api.core.virtual.gamemode.GameMode;
import net.astrocube.api.core.virtual.user.User;
import org.bukkit.entity.Player;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Optional;

@Singleton
public class CoreMatchRejoiner implements MatchRejoiner {

    private @Inject ActualMatchProvider actualMatchProvider;
    private @Inject MessageHandler<Player> messageHandler;
    private @Inject FindService<GameMode> findService;

    @Override
    public void rejoinMatch(User user, Player player) throws Exception {

        Optional<Match> match = actualMatchProvider.provide(user.getId());

        if (match.isPresent()) {

            UserMatchJoiner.Origin origin = UserMatchJoiner.checkOrigin(user.getId(), match.get());

            if (origin != UserMatchJoiner.Origin.WAITING) {
                messageHandler.send(player, AlertMode.ERROR,"game.rejoin.not-active");
                return;
            }

        } else {
            messageHandler.send(player, AlertMode.ERROR,"game.admin.not-active");
        }
    }

}
