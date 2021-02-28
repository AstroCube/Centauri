package net.astrocube.commons.bukkit.game.match;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import me.yushust.message.MessageHandler;
import net.astrocube.api.bukkit.game.match.MatchMapUpdater;
import net.astrocube.api.bukkit.translation.mode.AlertModes;
import net.astrocube.api.bukkit.virtual.game.match.Match;
import net.astrocube.api.bukkit.virtual.game.match.MatchDoc;
import net.astrocube.api.core.service.find.FindService;
import net.astrocube.api.core.service.update.UpdateService;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

@Singleton
public class CoreMatchMapUpdater implements MatchMapUpdater {

    private @Inject MessageHandler messageHandler;
    private @Inject UpdateService<Match, MatchDoc.Complete> updateService;
    private @Inject FindService<Match> findService;
    private @Inject Plugin plugin;

    @Override
    public void updateMatch(String match, String map, String requester) {
        findService.find(match).callback(matchCallback -> {
            if (!matchCallback.isSuccessful()) {
                alertError(requester);
            }
            matchCallback.ifSuccessful(matchResponse -> {
                matchResponse.setMap(map);
                updateService.update(matchResponse).callback(updateCallback -> {
                    if (!updateCallback.isSuccessful()) {
                        alertError(requester);
                    }
                });
            });
        });
    }

    private void alertError(String id) {

        Player player = Bukkit.getPlayerByIdentifier(id);

        if (player != null) {
            Bukkit.getScheduler().runTask(
                    plugin,
                    () -> {
                        messageHandler.sendIn(player, AlertModes.ERROR, "game.admin.lobby.map.error");
                        player.closeInventory();
                    });
        }
    }

}
