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

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CoreTagPacketHandler extends AbstractTagPacketHandler {

	public CoreTagPacketHandler(NametagRegistry renderedRegistry, Plugin plugin) {
		super(renderedRegistry, plugin);
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
						int id = spawnPacket.a;
						renderedRegistry.getRenderedForPlayer(player).values().forEach(rendered -> {
							if (id == rendered.getRecipient().getEntityId()) {
								Bukkit.getScheduler().runTaskLater(plugin, rendered::show, 5L);
							}
						});

					} else if (packetObject instanceof PacketPlayOutEntityDestroy) {
						PacketPlayOutEntityDestroy destroyPacket = (PacketPlayOutEntityDestroy) packetObject;

						int[] rawIds = destroyPacket.getEntityIds();
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