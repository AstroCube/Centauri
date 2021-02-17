package net.astrocube.commons.bukkit.listener.session;

import com.google.inject.Inject;
import net.astrocube.api.bukkit.authentication.event.SessionSwitchBroadcast;
import net.astrocube.api.bukkit.channel.admin.StaffLoginBroadcaster;
import net.astrocube.api.core.permission.PermissionBalancer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class StaffSessionLogListener implements Listener {

    private @Inject StaffLoginBroadcaster staffLoginBroadcaster;
    private @Inject PermissionBalancer permissionBalancer;

    @EventHandler
    public void onAuthenticationBroadcast(SessionSwitchBroadcast event) {
        if (
                PermissionBalancer.hasUnlinkedPermission(event.getSession().getUser(), permissionBalancer, "commons.staff.chat")
        ) {
            boolean important = PermissionBalancer.hasUnlinkedPermission(
                    event.getSession().getUser(), permissionBalancer, "commons.staff.chat.important");
            staffLoginBroadcaster.broadcastLogin(event.getSession().getUser(), important, event.getSession().isConnecting());
        }
    }

}
