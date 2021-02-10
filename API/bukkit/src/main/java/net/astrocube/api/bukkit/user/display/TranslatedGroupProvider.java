package net.astrocube.api.bukkit.user.display;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public interface TranslatedGroupProvider {

    /**
     * Retrieves translations for a certain group
     * @param id search
     * @param color to be used
     * @param player to use
     * @return translated format
     */
    TranslatedFlairFormat getGroupTranslations(Player player, String id, ChatColor color);

}
