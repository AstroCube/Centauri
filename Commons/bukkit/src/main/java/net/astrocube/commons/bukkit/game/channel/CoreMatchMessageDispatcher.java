package net.astrocube.commons.bukkit.game.channel;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import me.yushust.message.MessageHandler;
import net.astrocube.api.bukkit.game.channel.MatchMessageDispatcher;
import net.astrocube.api.bukkit.user.display.DisplayMatcher;
import net.astrocube.api.bukkit.user.display.TranslatedFlairFormat;
import net.astrocube.api.bukkit.virtual.channel.ChatChannelMessage;
import net.astrocube.api.bukkit.virtual.game.match.Match;
import net.astrocube.api.bukkit.virtual.game.match.MatchDoc;
import net.astrocube.api.core.service.find.FindService;
import net.astrocube.api.core.virtual.user.User;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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
                        dispatch(matchRecord.getSpectators(), channelMessage.getSender(), channelMessage.getMessage(), "game.spectator.chat");
                    }

                    if (origin.equalsIgnoreCase("playing")) {

                        Set<String> users = new HashSet<>();

                        if (matchRecord.getTeams().stream().anyMatch(t -> t.getMembers().size() != 1)) {

                            matchRecord.getTeams().stream()
                                    .filter(t -> t.getMembers().stream().anyMatch(
                                            m -> m.getUser().equalsIgnoreCase(channelMessage.getSender()))
                                    )
                                    .findAny()
                                    .ifPresent(t -> t.getMembers().forEach(m -> users.add(m.getUser())));

                        } else {
                            matchRecord.getTeams().stream().flatMap(
                                    team -> team.getMembers().stream().map(MatchDoc.TeamMember::getUser)
                            ).forEach(users::add);
                        }

                        dispatch(
                                users,
                                channelMessage.getSender(),
                                channelMessage.getMessage(),
                                "game.chat"
                        );

                    }

                })
        );

    }

    private void dispatch(Set<String> listeners, String senderId, String message, String translation) {

        userFindService.find(senderId).callback(userResponse -> {

            Player sender = Bukkit.getPlayerByIdentifier(senderId);

            if (sender != null) {

                listeners.forEach(spectator -> {

                    Player player = Bukkit.getPlayerByIdentifier(spectator);

                    if (player != null) {

                        String prefix = sender.getDisguisedName();

                        if (userResponse.isSuccessful() && userResponse.getResponse().isPresent()) {
                            TranslatedFlairFormat flairFormat = displayMatcher.getDisplay(player, userResponse.getResponse().get());
                            prefix = flairFormat.getColor() + flairFormat.getPrefix() + " " + ChatColor.WHITE + userResponse.getResponse().get().getDisplay();
                        }

                        messageHandler.sendReplacing(
                                player,
                                translation,
                                "%%player%%", prefix,
                                "%%message%%", message
                        );

                    }

                });

            }

        });

    }



}
