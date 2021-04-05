package net.astrocube.api.bukkit.user.skin;

import org.bukkit.entity.Player;

public interface CustomSkinRegistry {

    /**
     * Sets an skin for a player.
     * @param player to set
     * @param skin to set
     */
    void add(Player player, String skin);

}
