package net.astrocube.commons.bukkit.utils;

import net.kyori.text.Component;
import net.kyori.text.serializer.gson.GsonComponentSerializer;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.chat.ComponentSerializer;
import net.minecraft.network.chat.ChatMessage;
import net.minecraft.network.chat.ChatMessageType;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.PacketPlayOutChat;
import org.bukkit.craftbukkit.v1_17_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

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

	public static void sendActionBar(Player player, String message) {
		sendPacket(
			player, new PacketPlayOutChat(new ChatMessage(message), ChatMessageType.c, null)
		);
	}

	private static void sendPacket(Player player, Packet<?> packet) {
		((CraftPlayer) player)
			.getHandle()
			.b.sendPacket(packet);
	}

}