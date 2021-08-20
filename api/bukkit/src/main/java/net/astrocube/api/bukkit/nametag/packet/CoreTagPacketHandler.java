package net.astrocube.api.bukkit.nametag.packet;


import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import it.unimi.dsi.fastutil.ints.IntList;
import net.astrocube.api.bukkit.nametag.NametagRegistry;
import net.minecraft.network.protocol.game.PacketPlayOutEntityDestroy;
import net.minecraft.network.protocol.game.PacketPlayOutNamedEntitySpawn;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_17_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class CoreTagPacketHandler extends AbstractTagPacketHandler {

	public CoreTagPacketHandler(NametagRegistry renderedRegistry, Plugin plugin) {
		super(renderedRegistry, plugin);
	}

	@Override
	public void handle(Player player) {
		((CraftPlayer) player).getHandle()
			.b.a.k
			.pipeline()
			.addBefore("packet_handler", "nametag-handler", new ChannelDuplexHandler() {

				@Override
				public void write(ChannelHandlerContext channelHandlerContext, Object packetObject, ChannelPromise channelPromise) throws Exception {
					if (packetObject instanceof PacketPlayOutNamedEntitySpawn) {
						PacketPlayOutNamedEntitySpawn spawnPacket = (PacketPlayOutNamedEntitySpawn) packetObject;
						int id = spawnPacket.b();
						renderedRegistry.getRenderedForPlayer(player).values().forEach(rendered -> {
							if (id == rendered.getRecipient().getEntityId()) {
								Bukkit.getScheduler().runTaskLater(plugin, rendered::show, 5L);
							}
						});

					} else if (packetObject instanceof PacketPlayOutEntityDestroy) {
						PacketPlayOutEntityDestroy destroyPacket = (PacketPlayOutEntityDestroy) packetObject;

						IntList rawIds = destroyPacket.b();

						renderedRegistry.getRenderedForPlayer(player).values().forEach(rendered -> {
							if (rawIds.contains(rendered.getRecipient().getEntityId())) {
								rendered.hide();
							}
						});
					}
					super.write(channelHandlerContext, packetObject, channelPromise);
				}

			});
	}
}