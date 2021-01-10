package net.astrocube.lobby.nametag;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import net.astrocube.api.bukkit.lobby.nametag.LobbyNametagHandler;
import net.astrocube.api.bukkit.nametag.NametagRegistry;
import net.astrocube.api.bukkit.nametag.types.AbstractNametag;
import net.astrocube.api.bukkit.nametag.types.lobby.LobbyNametag;
import net.astrocube.api.bukkit.nametag.types.lobby.LobbyNametagRenderer;
import net.astrocube.api.bukkit.packet.PacketHandler;
import net.astrocube.api.bukkit.user.display.DisplayMatcher;
import net.astrocube.api.core.virtual.user.User;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.Map;

@Singleton
public class CoreLobbyNametagHandler implements LobbyNametagHandler {

    private final Map<CharSequence, AbstractNametag> tagList;
    private final DisplayMatcher displayMatcher;
    private final NametagRegistry nametagRegistry;
    private final PacketHandler packetHandler;
    private final LobbyNametagRenderer lobbyNametagRenderer;
    private final Plugin plugin;

    @Inject
    public CoreLobbyNametagHandler(
            DisplayMatcher displayMatcher, NametagRegistry nametagRegistry,
            LobbyNametagRenderer lobbyNametagRenderer, @Named("nametag") PacketHandler nametagPacketHandler,
            Plugin plugin
    ) {
        this.tagList = new HashMap<>();
        this.displayMatcher = displayMatcher;
        this.nametagRegistry = nametagRegistry;
        this.lobbyNametagRenderer = lobbyNametagRenderer;
        this.packetHandler = nametagPacketHandler;
        this.plugin = plugin;
    }

    @Override
    public void render(Player player, User user) {

        packetHandler.handle(player);

        LobbyNametag userTag = LobbyNametag
                .builder(player)
                .prefix(displayMatcher.getRealmDisplay(user).getSymbol())
                .build();

        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            tagList.values().forEach(tag ->
                    nametagRegistry.submit(lobbyNametagRenderer.render(userTag, player)));
            tagList.put(user.getId(), userTag);

            Bukkit.getOnlinePlayers().forEach(online -> {
                if (!online.getDatabaseIdentifier().equals(player.getDatabaseIdentifier())) {
                    nametagRegistry.submit(lobbyNametagRenderer.render(userTag, player));
                }
            });

        }, 5L);

    }

    @Override
    public void remove(Player player) {
        tagList.remove(player.getDatabaseIdentifier());
        nametagRegistry.delete(player.getDatabaseIdentifier());
    }

}
