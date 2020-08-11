package net.astrocube.commons.bukkit.session;

import net.astrocube.api.bukkit.session.InvalidSessionMessageMatcher;
import net.astrocube.api.core.virtual.user.User;

public class CoreInvalidSessionMessageMatches implements InvalidSessionMessageMatcher {

    @Override
    public String generateSessionMessage(User user) {
        return "";
    }
}
