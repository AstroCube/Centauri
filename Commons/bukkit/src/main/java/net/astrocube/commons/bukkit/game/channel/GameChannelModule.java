package net.astrocube.commons.bukkit.game.channel;

import me.fixeddev.inject.ProtectedModule;
import net.astrocube.api.bukkit.game.channel.MatchChannelProvider;
import net.astrocube.api.bukkit.game.channel.MatchMessageBroadcaster;
import net.astrocube.api.bukkit.game.channel.MatchMessageDispatcher;

public class GameChannelModule extends ProtectedModule {

    @Override
    public void configure() {
        bind(MatchChannelProvider.class).to(CoreMatchChannelProvider.class);
        bind(MatchMessageBroadcaster.class).to(ChatMatchMessageBroadcaster.class);
        bind(MatchMessageDispatcher.class).to(CoreMatchMessageDispatcher.class);
    }

}
