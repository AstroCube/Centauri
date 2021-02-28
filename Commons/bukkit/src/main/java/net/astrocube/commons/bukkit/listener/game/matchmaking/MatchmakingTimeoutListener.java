package net.astrocube.commons.bukkit.listener.game.matchmaking;

import com.google.inject.Inject;
import me.yushust.message.MessageHandler;
import net.astrocube.api.bukkit.game.event.matchmaking.MatchmakingErrorEvent;
import net.astrocube.api.bukkit.translation.mode.AlertModes;
import net.astrocube.commons.bukkit.game.GameControlHelper;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class MatchmakingTimeoutListener implements Listener {

    private @Inject MessageHandler messageHandler;

    @EventHandler
    public void onMatchmakingError(MatchmakingErrorEvent event) {
        GameControlHelper.getPlayersFromRequest(
                event.getMatchmakingError().getRequest()).forEach(player -> messageHandler.sendIn(player, AlertModes.ERROR, "game.matchmaking.timeout"));
    }

}
