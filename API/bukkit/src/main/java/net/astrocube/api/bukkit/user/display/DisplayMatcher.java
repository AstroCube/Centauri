package net.astrocube.api.bukkit.user.display;

import net.astrocube.api.core.virtual.group.Group;
import net.astrocube.api.core.virtual.user.User;

public interface DisplayMatcher {

    /**
     * Obtain the flair to be displayed in a certain realm
     * @param user to be displayed
     * @return flair to be processed
     */
    Group.Flair getRealmDisplay(User user);

}
