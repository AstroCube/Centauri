package net.astrocube.commons.bukkit.utils;

import net.kyori.text.Component;
import net.kyori.text.serializer.gson.GsonComponentSerializer;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.chat.ComponentSerializer;

public final class MessageUtils {

	private MessageUtils() {
		throw new UnsupportedOperationException("This class should not be instantiated");
	}

	public static BaseComponent[] kyoriToBungee(Component component) {
		GsonComponentSerializer componentSerializer = GsonComponentSerializer.INSTANCE;

		String serializedComponent = componentSerializer.serialize(component);
		BaseComponent[] components = ComponentSerializer.parse(serializedComponent);

		return components;
	}

}