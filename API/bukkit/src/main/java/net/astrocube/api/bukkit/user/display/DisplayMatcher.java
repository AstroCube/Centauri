package net.astrocube.api.bukkit.user.display;

import net.astrocube.api.core.virtual.group.Group;
import net.astrocube.api.core.virtual.user.User;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.Locale;

public interface DisplayMatcher {

    /**
     * Obtain the translated highest flair of a user
     * @param user to be displayed
     * @param owner of the translation
     * @return translations to be processed
     */
    TranslatedFlairFormat getDisplay(Player owner, User user);

    static ChatColor getColor(Group group) {
        try {
            return ChatColor.valueOf(group.getMinecraftColor().toUpperCase(Locale.ROOT));
        } catch (Exception ignore) {}
        return ChatColor.GRAY;
    }

}
