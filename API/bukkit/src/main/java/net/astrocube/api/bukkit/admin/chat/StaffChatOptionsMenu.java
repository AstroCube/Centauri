package net.astrocube.api.bukkit.admin.chat;

import org.bukkit.entity.Player;

public interface StaffChatOptionsMenu {

    /**
     * Generates menu of staff chat for modification
     * @param player where information will be taken
     * @return inventory loading the menu
     */
    void generateMenu(Player player);

}
