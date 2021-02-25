package net.astrocube.commons.bukkit.game.channel;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import me.yushust.message.MessageHandler;
import net.astrocube.api.bukkit.game.channel.MatchChannelProvider;
import net.astrocube.api.bukkit.game.channel.MatchMessageBroadcaster;
import net.astrocube.api.bukkit.game.channel.MatchMessageDispatcher;
import net.astrocube.api.bukkit.game.match.ActualMatchCache;
import net.astrocube.api.bukkit.game.match.UserMatchJoiner;
import net.astrocube.api.bukkit.translation.mode.AlertModes;
import net.astrocube.api.bukkit.virtual.channel.ChatChannel;
import net.astrocube.api.bukkit.virtual.channel.ChatChannelMessage;
import net.astrocube.api.bukkit.virtual.channel.ChatChannelMessageDoc;
import net.astrocube.api.bukkit.virtual.game.match.Match;
import net.astrocube.api.core.service.create.CreateService;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Singleton
public class ChatMatchMessageBroadcaster implements MatchMessageBroadcaster {

    private @Inject CreateService<ChatChannelMessage, ChatChannelMessageDoc.Creation> createService;
    private @Inject MatchChannelProvider matchChannelProvider;
    private @Inject ActualMatchCache actualMatchCache;
    private @Inject MatchMessageDispatcher matchMessageDispatcher;
    private @Inject MessageHandler messageHandler;

    @Override
    public void sendMessage(String message, Player player) throws Exception {
        sendMessage(message, player, false, false);
    }

    @Override
    public void sendMessage(String message, Player player, boolean shout, boolean all) throws Exception {

        Optional<Match> matchOptional = actualMatchCache.get(player.getDatabaseIdentifier());

        if (!matchOptional.isPresent()) {
            messageHandler.sendIn(player, AlertModes.ERROR, "game.spectator.error");
            return;
        }

        Match match = matchOptional.get();

        UserMatchJoiner.Origin origin =
                UserMatchJoiner.checkOrigin(player.getDatabaseIdentifier(), match);

        Optional<ChatChannel> channel = matchChannelProvider.retrieveChannel(match.getId());

        if (!channel.isPresent()) {
            messageHandler.sendIn(player, AlertModes.ERROR, "game.spectator.error");
            return;
        }

        ChatChannelMessageDoc.Creation channelMessage = new ChatChannelMessageDoc.Creation() {
            @Override
            public String getSender() {
                return player.getDatabaseIdentifier();
            }

            @Override
            public String getMessage() {
                return message;
            }

            @Override
            public String getChannel() {
                return channel.get().getId();
            }

            @Override
            public Origin getOrigin() {
                return Origin.INGAME;
            }

            @Override
            public Map<String, Object> getMeta() {

                Map<String, Object> meta = new HashMap<>();

                meta.put("origin", origin);

                if (shout) {

                    meta.put("shout", true);

                    if (all) {
                        meta.put("all", true);
                    }

                }

                return meta;
            }
        };

        matchMessageDispatcher.dispatch(createService.createSync(channelMessage), match.getId());

    }

}
