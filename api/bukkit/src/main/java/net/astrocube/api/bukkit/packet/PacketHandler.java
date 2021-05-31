package net.astrocube.api.bukkit.packet;

import org.bukkit.entity.Player;

public interface PacketHandler {

	void handle(Player player);

}