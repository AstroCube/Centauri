package net.astrocube.api.bukkit.game.channel;

import net.astrocube.api.bukkit.virtual.channel.ChatChannelMessage;

public interface MatchMessageDispatcher {

	void dispatch(ChatChannelMessage channelMessage, String match);

}
