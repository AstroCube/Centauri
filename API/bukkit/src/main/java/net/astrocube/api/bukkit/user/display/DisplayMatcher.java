package net.astrocube.api.bukkit.user.display;

import net.astrocube.api.core.virtual.group.Group;
import net.astrocube.api.core.virtual.user.User;
import org.bukkit.ChatColor;

import java.util.Locale;

public interface DisplayMatcher {

    /**
     * Obtain the flair to be displayed in a certain realm
     * @param user to be displayed
     * @return flair to be processed
     */
    Group.Flair getRealmDisplay(User user);

    static ChatColor getColor(Group.Flair flair) {
        try {
            return ChatColor.valueOf(flair.getColor().toUpperCase(Locale.ROOT));
        } catch (Exception ignore) {}
        return ChatColor.GRAY;
    }

}
