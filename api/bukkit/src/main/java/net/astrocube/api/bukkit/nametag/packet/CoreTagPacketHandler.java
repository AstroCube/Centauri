package net.astrocube.api.bukkit.nametag.packet;


import net.astrocube.api.bukkit.nametag.NametagRegistry;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityDestroy;
import net.minecraft.server.v1_8_R3.PacketPlayOutNamedEntitySpawn;
import net.seocraft.lib.netty.channel.ChannelDuplexHandler;
import net.seocraft.lib.netty.channel.ChannelHandlerContext;
import net.seocraft.lib.netty.channel.ChannelPromise;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CoreTagPacketHandler extends AbstractTagPacketHandler {

	private static Field SPAWN_ID_FIELD;
	private static Field DESTROY_ID_FIELD;

	public CoreTagPacketHandler(NametagRegistry renderedRegistry, Plugin plugin) {
		super(renderedRegistry, plugin);

		try {
			SPAWN_ID_FIELD = PacketPlayOutNamedEntitySpawn.class.getDeclaredField("a");
			SPAWN_ID_FIELD.setAccessible(true);

			DESTROY_ID_FIELD = PacketPlayOutEntityDestroy.class.getDeclaredField("a");
			DESTROY_ID_FIELD.setAccessible(true);
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}

	@Override
	public void handle(Player player) {
		((CraftPlayer) player).getHandle()
			.playerConnection
			.networkManager
			.channel
			.pipeline()
			.addBefore("packet_handler", "nametag-handler", new ChannelDuplexHandler() {

				@Override
				public void write(ChannelHandlerContext channelHandlerContext, Object packetObject, ChannelPromise channelPromise) throws Exception {
					if (packetObject instanceof PacketPlayOutNamedEntitySpawn) {
						PacketPlayOutNamedEntitySpawn spawnPacket = (PacketPlayOutNamedEntitySpawn) packetObject;
						int id = (int) SPAWN_ID_FIELD.get(spawnPacket);
						renderedRegistry.getRenderedForPlayer(player).values().forEach(rendered -> {
							if (id == rendered.getRecipient().getEntityId()) {
								Bukkit.getScheduler().runTaskLater(plugin, rendered::show, 5L);
							}
						});

					} else if (packetObject instanceof PacketPlayOutEntityDestroy) {
						PacketPlayOutEntityDestroy destroyPacket = (PacketPlayOutEntityDestroy) packetObject;

						int[] rawIds = (int[]) DESTROY_ID_FIELD.get(destroyPacket);
						List<Integer> ids = Arrays.stream(rawIds).boxed().collect(Collectors.toList());

						renderedRegistry.getRenderedForPlayer(player).values().forEach(rendered -> {
							if (ids.contains(rendered.getRecipient().getEntityId())) {
								rendered.hide();
							}
						});
					}
					super.write(channelHandlerContext, packetObject, channelPromise);
				}

			});
	}
}