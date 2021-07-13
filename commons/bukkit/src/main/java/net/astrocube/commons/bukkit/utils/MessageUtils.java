package net.astrocube.commons.bukkit.utils;

import net.kyori.text.Component;
import net.kyori.text.serializer.gson.GsonComponentSerializer;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.chat.ComponentSerializer;
import net.minecraft.server.v1_8_R3.ChatMessage;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
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
			player, new PacketPlayOutChat(new ChatMessage(message), (byte) 2)
		);
	}

	private static void sendPacket(Player player, Packet<?> packet) {
		((CraftPlayer) player)
			.getHandle()
			.playerConnection
			.sendPacket(packet);
	}

}