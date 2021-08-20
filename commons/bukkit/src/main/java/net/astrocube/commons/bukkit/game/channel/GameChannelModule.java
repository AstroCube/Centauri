package net.astrocube.commons.bukkit.game.channel;

import net.astrocube.inject.ProtectedModule;
import net.astrocube.api.bukkit.game.channel.MatchChannelProvider;
import net.astrocube.api.bukkit.game.channel.MatchMessageBroadcaster;
import net.astrocube.api.bukkit.game.channel.MatchMessageDispatcher;
import net.astrocube.api.bukkit.game.channel.MatchShoutoutCooldown;

public class GameChannelModule extends ProtectedModule {

	@Override
	public void configure() {
		bind(MatchChannelProvider.class).to(CoreMatchChannelProvider.class);
		bind(MatchMessageBroadcaster.class).to(ChatMatchMessageBroadcaster.class);
		bind(MatchMessageDispatcher.class).to(CoreMatchMessageDispatcher.class);
		bind(MatchShoutoutCooldown.class).to(CoreMatchShoutoutCooldown.class);
	}

}
