package net.astrocube.commons.bukkit.cloud.dummy;

import net.astrocube.api.core.cloud.CloudTeleport;

public class DummyCloudTeleport implements CloudTeleport {
    @Override
    public void teleportToServer(String server, String player) {

    }

    @Override
    public void teleportToGroup(String group, String player) {

    }

    @Override
    public String getServerFromGroup(String group) {
        return "";
    }
}
