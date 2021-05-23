package net.astrocube.commons.bukkit.virtual;

import com.google.inject.TypeLiteral;
import me.fixeddev.inject.ProtectedModule;
import net.astrocube.api.bukkit.virtual.channel.ChatChannel;
import net.astrocube.api.bukkit.virtual.channel.ChatChannelDoc;
import net.astrocube.api.bukkit.virtual.channel.ChatChannelMessage;
import net.astrocube.api.bukkit.virtual.channel.ChatChannelMessageDoc;
import net.astrocube.api.core.message.ChannelBinder;
import net.astrocube.commons.bukkit.channel.ChatChannelMessageHandler;
import net.astrocube.commons.core.model.binder.ModelBinder;
import net.astrocube.commons.core.service.CoreModelService;

public class ChannelModelModule extends ProtectedModule implements ModelBinder, ChannelBinder {

	@Override
	protected void configure() {

		bindModel(ChatChannel.class, ChatChannelDoc.Creation.class)
			.toSingleService(new TypeLiteral<CoreModelService<ChatChannel, ChatChannelDoc.Creation>>() {});

		bindModel(ChatChannelMessage.class, ChatChannelMessageDoc.Creation.class)
			.toSingleService(new TypeLiteral<CoreModelService<ChatChannelMessage, ChatChannelMessageDoc.Creation>>() {});

		bindChannel(ChatChannelMessage.class)
			.registerListener(ChatChannelMessageHandler.class);
	}
}