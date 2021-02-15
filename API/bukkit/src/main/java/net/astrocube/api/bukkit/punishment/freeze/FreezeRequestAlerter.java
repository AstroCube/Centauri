package net.astrocube.api.bukkit.punishment.freeze;

import org.bukkit.entity.Player;

public interface FreezeRequestAlerter {

    /**
     * Alert player of freezing
     * @param player to be alerted
     */
    void alert(Player player);

    /**
     * Alert player of unfreezing
     * @param player to be alerted
     */
    void alertUnfreeze(Player player);

}
