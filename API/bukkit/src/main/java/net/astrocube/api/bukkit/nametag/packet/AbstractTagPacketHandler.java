package net.astrocube.api.bukkit.nametag.packet;

import net.astrocube.api.bukkit.packet.PacketHandler;
import net.astrocube.api.bukkit.nametag.NametagRegistry;
import org.bukkit.plugin.Plugin;

public abstract class AbstractTagPacketHandler implements PacketHandler {

    protected final NametagRegistry renderedRegistry;
    protected final Plugin plugin;

    public AbstractTagPacketHandler(NametagRegistry renderedRegistry, Plugin plugin) {
        this.renderedRegistry = renderedRegistry;
        this.plugin = plugin;
    }
}