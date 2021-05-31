package net.astrocube.lobby.nametag;

import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import me.fixeddev.inject.ProtectedModule;
import net.astrocube.api.bukkit.lobby.nametag.LobbyNametagHandler;
import net.astrocube.api.bukkit.nametag.NametagRegistry;
import net.astrocube.api.bukkit.nametag.packet.CoreTagPacketHandler;
import net.astrocube.api.bukkit.packet.PacketHandler;
import org.bukkit.plugin.Plugin;

public class NametagModule extends ProtectedModule {

	@Override
	public void configure() {
		bind(LobbyNametagHandler.class).to(CoreLobbyNametagHandler.class);
	}

	@Provides
	@Singleton
	@Named("nametag")
	public PacketHandler provideNametagPacketHandler(NametagRegistry registry, Plugin plugin) {
		return new CoreTagPacketHandler(registry, plugin);
	}
}