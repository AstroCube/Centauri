package net.astrocube.commons.bukkit.game.channel;

import com.google.inject.Singleton;
import net.astrocube.api.bukkit.channel.ChatMessageInterceptor;
import net.astrocube.api.bukkit.virtual.channel.ChatChannel;
import net.astrocube.api.bukkit.virtual.channel.ChatChannelMessage;

@Singleton
public class MatchChannelInterceptor implements ChatMessageInterceptor {
    @Override
    public void intercept(ChatChannel channel, ChatChannelMessage message) {

        if (!channel.getName().contains("match_")) {
            return;
        }



    }
}
