package net.astrocube.api.bukkit.lobby.nametag;

import net.astrocube.api.core.virtual.user.User;
import org.bukkit.entity.Player;

public interface LobbyNametagHandler {

    void render(Player player, User user);

    void remove(Player player);

}
