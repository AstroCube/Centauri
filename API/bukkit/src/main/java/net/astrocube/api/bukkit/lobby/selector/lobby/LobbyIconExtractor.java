package net.astrocube.api.bukkit.lobby.selector.lobby;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import team.unnamed.gui.api.item.ItemClickable;

public interface LobbyIconExtractor {

    /**
     * Extract required info to generate a {@link ItemStack} with messages for a menu
     * @param wrapper to be extracted
     * @param player where language will be provided
     * @param position to be placed
     * @return configured icon
     */
    ItemClickable getLobbyIcon(LobbySelectorWrapper wrapper, Player player, int position);

}
