package net.astrocube.commons.bukkit.game.channel;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import me.yushust.message.MessageHandler;
import net.astrocube.api.bukkit.game.channel.MatchMessageDispatcher;
import net.astrocube.api.bukkit.game.match.control.MatchParticipantsProvider;
import net.astrocube.api.bukkit.user.display.DisplayMatcher;
import net.astrocube.api.bukkit.user.display.TranslatedFlairFormat;
import net.astrocube.api.bukkit.virtual.channel.ChatChannelMessage;
import net.astrocube.api.bukkit.virtual.game.match.Match;
import net.astrocube.api.core.service.find.FindService;
import net.astrocube.api.core.virtual.user.User;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;

@Singleton
public class CoreMatchMessageDispatcher implements MatchMessageDispatcher {

    private @Inject FindService<Match> findService;
    private @Inject FindService<User> userFindService;
    private @Inject DisplayMatcher displayMatcher;
    private @Inject MessageHandler messageHandler;
    private @Inject Plugin plugin;

    @Override
    public void dispatch(ChatChannelMessage channelMessage, String match) {

        findService.find(match).callback(matchRequest ->
                matchRequest.ifSuccessful(matchRecord -> {

                    String origin = (String) channelMessage.getMeta().get("origin");

                    if (origin.equalsIgnoreCase("waiting")) {
                        dispatch(
                                MatchParticipantsProvider.getWaitingIds(matchRecord),
                                channelMessage.getSender(),
                                channelMessage.getMessage(),
                                "game.chat"
                        );
                    }

                    if (origin.equalsIgnoreCase("spectating")) {

                        if (channelMessage.getMeta().containsKey("shout") && channelMessage.getMeta().containsKey("all")) {
                            dispatch(
                                    MatchParticipantsProvider.getInvolvedIds(matchRecord),
                                    channelMessage.getSender(),
                                    channelMessage.getMessage(),
                                    "game.shout"
                            );
                            return;
                        }

                        dispatch(matchRecord.getSpectators(), channelMessage.getSender(), channelMessage.getMessage(), "game.spectator.chat");

                    }

                    if (origin.equalsIgnoreCase("playing")) {

                        Set<String> users = new HashSet<>();

                        if (channelMessage.getMeta().containsKey("shout")) {

                            users.addAll(MatchParticipantsProvider.getOnlineIds(matchRecord));

                            if (channelMessage.getMeta().containsKey("all")) {
                                users.addAll(MatchParticipantsProvider.getInvolvedIds(matchRecord));
                            }

                            dispatch(
                                    users,
                                    channelMessage.getSender(),
                                    channelMessage.getMessage(),
                                    "game.shout"
                            );

                            return;

                        }

                        if (matchRecord.getTeams().stream().anyMatch(t -> t.getMembers().size() > 1)) {

                            matchRecord.getTeams().stream()
                                    .filter(t -> t.getMembers().stream().anyMatch(
                                            m -> m.getUser().equalsIgnoreCase(channelMessage.getSender()))
                                    )
                                    .findAny()
                                    .ifPresent(t -> t.getMembers().forEach(m -> users.add(m.getUser())));

                        } else {
                            users.addAll(MatchParticipantsProvider.getOnlineIds(matchRecord));
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

                        plugin.getLogger().log(Level.INFO, "{0}: {1}", new String[]{player.getName(), message});
                        messageHandler.sendReplacing(
                                player,
                                translation,
                                "%player%", prefix,
                                "%message%", message
                        );

                    }

                });

            }

        });

    }



}
