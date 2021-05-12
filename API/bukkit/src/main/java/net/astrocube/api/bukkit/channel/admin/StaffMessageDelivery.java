package net.astrocube.api.bukkit.channel.admin;

import net.astrocube.api.bukkit.virtual.channel.ChatChannelMessage;

public interface StaffMessageDelivery {

	void deliver(ChatChannelMessage message);

}