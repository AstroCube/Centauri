package net.astrocube.commons.bukkit.channel.admin;

import net.astrocube.api.bukkit.channel.ChatMessageInterceptor;
import net.astrocube.api.bukkit.channel.admin.StaffMessageDelivery;
import net.astrocube.api.bukkit.virtual.channel.ChatChannel;
import net.astrocube.api.bukkit.virtual.channel.ChatChannelMessage;

import javax.inject.Inject;

public class StaffChatMessageInterceptor implements ChatMessageInterceptor {

	private @Inject StaffMessageDelivery staffMessageDelivery;

	@Override
	public void intercept(ChatChannel channel, ChatChannelMessage message) {
		if (!channel.getName().equalsIgnoreCase("ac-staff")) return;

		this.staffMessageDelivery.deliver(message);
	}
}