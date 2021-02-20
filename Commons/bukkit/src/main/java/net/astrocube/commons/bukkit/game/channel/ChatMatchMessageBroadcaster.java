package net.astrocube.commons.bukkit.game.channel;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.astrocube.api.bukkit.game.channel.MatchChannelProvider;
import net.astrocube.api.bukkit.game.channel.MatchMessageBroadcaster;
import net.astrocube.api.bukkit.game.match.ActualMatchCache;
import net.astrocube.api.bukkit.game.match.UserMatchJoiner;
import net.astrocube.api.bukkit.virtual.channel.ChatChannel;
import net.astrocube.api.bukkit.virtual.channel.ChatChannelMessage;
import net.astrocube.api.bukkit.virtual.channel.ChatChannelMessageDoc;
import net.astrocube.api.bukkit.virtual.game.match.Match;
import net.astrocube.api.core.message.Channel;
import net.astrocube.api.core.message.Messenger;
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

    private final Channel<ChatChannelMessage> redisMessageChannel;

    @Inject
    ChatMatchMessageBroadcaster(Messenger messenger) {
        this.redisMessageChannel = messenger.getChannel(ChatChannelMessage.class);
    }


    @Override
    public void sendMessage(String message, Player player) throws Exception {

        Optional<Match> matchOptional = actualMatchCache.get(player.getDatabaseIdentifier());

        if (!matchOptional.isPresent()) {
            return;
        } else {

            Match match = matchOptional.get();

            UserMatchJoiner.Origin origin =
                    UserMatchJoiner.checkOrigin(player.getDatabaseIdentifier(), match);

            Optional<ChatChannel> channel = matchChannelProvider.retrieveChannel(match.getId());

            if (!channel.isPresent()) {
                return;
            } else {

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

                        return meta;
                    }
                };

                redisMessageChannel.sendMessage(createService.createSync(channelMessage), new HashMap<>());

            }


        }

    }

}
