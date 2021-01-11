package net.astrocube.commons.core.cloud;

import cloud.timo.TimoCloud.api.TimoCloudAPI;
import cloud.timo.TimoCloud.api.objects.ServerGroupObject;
import cloud.timo.TimoCloud.api.objects.ServerObject;

import java.util.Optional;

public class CloudUtils {

    public static Optional<ServerObject> getServerFromGroup(String group) {
        Optional<ServerGroupObject> groupObject = getServerGroup(group);
        return groupObject.flatMap(serverGroupObject -> serverGroupObject.getServers().stream().findAny());
    }

    public static Optional<ServerGroupObject> getServerGroup(String group) {
        return TimoCloudAPI.getUniversalAPI()
                .getServerGroups().stream()
                .filter(g -> g.getName().equalsIgnoreCase(group))
                .findAny();
    }

}
