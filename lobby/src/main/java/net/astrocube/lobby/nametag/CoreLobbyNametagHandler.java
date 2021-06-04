package net.astrocube.lobby.nametag;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import me.yushust.message.MessageHandler;
import net.astrocube.api.bukkit.lobby.nametag.LobbyNametagHandler;
import net.astrocube.api.bukkit.nametag.NametagRegistry;
import net.astrocube.api.bukkit.nametag.types.lobby.LobbyNametag;
import net.astrocube.api.bukkit.nametag.types.lobby.LobbyNametagRenderer;
import net.astrocube.api.bukkit.packet.PacketHandler;
import net.astrocube.api.bukkit.user.display.DisplayMatcher;
import net.astrocube.api.bukkit.user.display.TranslatedFlairFormat;
import net.astrocube.api.core.service.find.FindService;
import net.astrocube.api.core.virtual.user.User;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.Locale;

@Singleton
public class CoreLobbyNametagHandler implements LobbyNametagHandler {

	private @Inject DisplayMatcher displayMatcher;
	private @Inject NametagRegistry nametagRegistry;
	private @Inject FindService<User> findService;
	private @Named("nametag")
	@Inject
	PacketHandler packetHandler;
	private @Inject LobbyNametagRenderer lobbyNametagRenderer;
	private @Inject Plugin plugin;
	private @Inject MessageHandler messageHandler;

	@Override
	public void render(Player player, User user) {

		packetHandler.handle(player);

		player.setPlayerListName(displayMatcher.getDisplay(player, user).getColor() + user.getDisplay());

		Bukkit.getOnlinePlayers().forEach(online -> {

			if (!online.getDatabaseIdentifier().equals(player.getDatabaseIdentifier())) {

				findService.find(online.getDatabaseIdentifier()).callback(onlineCallback -> {

					onlineCallback.ifSuccessful(onlineUser -> {

						LobbyNametag ownerTag = LobbyNametag
							.builder(player)
							.prefix(getTranslatedTag(online, user))
							.build();

						LobbyNametag onlineTag = LobbyNametag
							.builder(online)
							.prefix(getTranslatedTag(player, onlineUser))
							.build();

						Bukkit.getScheduler().runTaskLater(plugin, () -> {
							nametagRegistry.submit(lobbyNametagRenderer.render(ownerTag, online));
							nametagRegistry.submit(lobbyNametagRenderer.render(onlineTag, player));
						}, 5L);

					});

				});

			}

		});

	}

	@Override
	public void remove(Player player) {
		nametagRegistry.delete(player.getDatabaseIdentifier());
	}

	private String getTranslatedTag(Player viewer, User user) {
		TranslatedFlairFormat flairFormat = displayMatcher.getDisplay(viewer, user);
		return flairFormat.getPrefix().equalsIgnoreCase(flairFormat.getColor() + "") ?
			flairFormat.getColor() + "" :
			messageHandler.replacing(
				viewer, "prefix",
				"%color%", (flairFormat.getColor() + ""),
				"%prefix%", flairFormat.getPrefix().toUpperCase(Locale.ROOT)
			);
	}

}
