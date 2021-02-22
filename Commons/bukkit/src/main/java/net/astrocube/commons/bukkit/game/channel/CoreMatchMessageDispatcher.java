package net.astrocube.commons.bukkit.game.channel;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import me.yushust.message.MessageHandler;
import net.astrocube.api.bukkit.game.channel.MatchMessageDispatcher;
import net.astrocube.api.bukkit.user.display.DisplayMatcher;
import net.astrocube.api.bukkit.user.display.TranslatedFlairFormat;
import net.astrocube.api.bukkit.virtual.channel.ChatChannelMessage;
import net.astrocube.api.bukkit.virtual.game.match.Match;
import net.astrocube.api.core.service.find.FindService;
import net.astrocube.api.core.virtual.user.User;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Set;

@Singleton
public class CoreMatchMessageDispatcher implements MatchMessageDispatcher {

    private @Inject FindService<Match> findService;
    private @Inject FindService<User> userFindService;
    private @Inject DisplayMatcher displayMatcher;
    private @Inject MessageHandler messageHandler;

    @Override
    public void dispatch(ChatChannelMessage channelMessage, String match) {

        findService.find(match).callback(matchRequest ->
                matchRequest.ifSuccessful(matchRecord -> {

                    String origin = (String) channelMessage.getMeta().get("origin");

                    if (origin.equalsIgnoreCase("spectating")) {
                        dispatchSpectator(matchRecord.getSpectators(), channelMessage.getSender(), channelMessage.getMessage());
                    }

                })
        );

    }

    private void dispatchSpectator(Set<String> spectators, String senderId, String message) {

        userFindService.find(senderId).callback(userResponse -> {

            Player sender = Bukkit.getPlayerByIdentifier(senderId);

            if (sender != null) {

                spectators.forEach(spectator -> {

                    Player player = Bukkit.getPlayerByIdentifier(spectator);

                    if (player != null) {

                        String prefix = sender.getDisguisedName();

                        if (userResponse.isSuccessful() && userResponse.getResponse().isPresent()) {
                            TranslatedFlairFormat flairFormat = displayMatcher.getDisplay(player, userResponse.getResponse().get());
                            prefix = flairFormat.getColor() + flairFormat.getPrefix() + " " + userResponse.getResponse().get().getDisplay();
                        }

                        messageHandler.sendReplacing(
                                player,
                                "game.spectator.chat",
                                "%%player%%", prefix,
                                "%%message%%", message
                        );

                    }

                });

            }

        });

    }

}
