package net.astrocube.commons.bukkit.cloud;

import cloud.timo.TimoCloud.api.TimoCloudAPI;
import com.google.inject.Singleton;
import net.astrocube.api.core.cloud.CloudTeleport;
import net.astrocube.commons.core.cloud.CloudUtils;

import java.util.Optional;

@Singleton
public class BukkitCloudTeleport implements CloudTeleport {

    @Override
    public void teleportToServer(String server, String player) {
        Optional.of(TimoCloudAPI.getUniversalAPI().getServer(server))
                .ifPresent(serverObject -> TimoCloudAPI.getUniversalAPI().getPlayer(player).sendToServer(serverObject));
    }

    @Override
    public void teleportToGroup(String group, String player) {
        CloudUtils.getServerFromGroup(group).ifPresent(groupObject ->
                TimoCloudAPI.getUniversalAPI().getPlayer(player).sendToServer(groupObject));
    }

}
