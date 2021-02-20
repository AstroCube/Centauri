package net.astrocube.commons.bukkit.game.channel;

import me.fixeddev.inject.ProtectedModule;
import net.astrocube.api.bukkit.game.channel.MatchChannelProvider;

public class GameChannelModule extends ProtectedModule {

    @Override
    public void configure() {
        bind(MatchChannelProvider.class).to(CoreMatchChannelProvider.class);
    }

}
