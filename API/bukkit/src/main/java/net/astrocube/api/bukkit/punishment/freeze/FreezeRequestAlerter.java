package net.astrocube.api.bukkit.punishment.freeze;

import org.bukkit.entity.Player;

public interface FreezeRequestAlerter {

    /**
     *
     * @param player
     */
    void alert(Player player);

}
