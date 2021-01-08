package net.astrocube.api.bukkit.channel.admin;

import net.astrocube.api.core.virtual.user.User;

public interface StaffLoginBroadcaster {

    void broadcastLogin(User session, boolean important);

    void broadcastLogout(User session, boolean important);

}