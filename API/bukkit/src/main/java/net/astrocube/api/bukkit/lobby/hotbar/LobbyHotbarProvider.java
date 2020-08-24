package net.astrocube.api.bukkit.lobby.hotbar;

import net.astrocube.api.core.virtual.user.User;
import org.bukkit.entity.Player;

public interface LobbyHotbarProvider {

    /**
     * Provides basic lobby hotbar to the user
     * @param user where the hotbar will be established
     * @param player to be provided at Minecraft
     */
    void setup(User user, Player player);

}
