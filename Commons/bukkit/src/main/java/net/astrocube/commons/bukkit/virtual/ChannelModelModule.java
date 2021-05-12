package net.astrocube.commons.bukkit.virtual;

import com.google.inject.TypeLiteral;
import me.fixeddev.inject.ProtectedModule;
import net.astrocube.api.bukkit.virtual.channel.ChatChannel;
import net.astrocube.api.bukkit.virtual.channel.ChatChannelDoc;
import net.astrocube.api.bukkit.virtual.channel.ChatChannelMessage;
import net.astrocube.api.bukkit.virtual.channel.ChatChannelMessageDoc;
import net.astrocube.api.core.message.ChannelBinder;
import net.astrocube.api.core.utils.ResolvableType;
import net.astrocube.commons.bukkit.channel.ChatChannelMessageHandler;
import net.astrocube.commons.core.model.binder.ModelBinderModule;
import net.astrocube.commons.core.service.CoreModelService;

public class ChannelModelModule extends ProtectedModule implements ModelBinderModule, ChannelBinder {

	@Override
	protected void configure() {
		bindModel(ChatChannel.class, ChatChannelDoc.Creation.class, model -> {
			TypeLiteral<CoreModelService<ChatChannel, ChatChannelDoc.Creation>> serviceTypeLiteral =
				new ResolvableType<CoreModelService<ChatChannel, ChatChannelDoc.Creation>>() {
				};
			model.bind(serviceTypeLiteral);
		});

		bindModel(ChatChannelMessage.class, ChatChannelMessageDoc.Creation.class, model -> {
			TypeLiteral<CoreModelService<ChatChannelMessage, ChatChannelMessageDoc.Creation>> serviceTypeLiteral =
				new ResolvableType<CoreModelService<ChatChannelMessage, ChatChannelMessageDoc.Creation>>() {
				};
			model.bind(serviceTypeLiteral);
		});

		bindChannel(ChatChannelMessage.class).registerHandler(new ChatChannelMessageHandler());
	}
}