package net.astrocube.api.bukkit.tablist;

import org.bukkit.entity.Player;

public interface TablistCompoundApplier {

    /**
     * Applies the compound to a player
     * @param player where the tablist will be updated.
     */
    void apply(Player player);

}
