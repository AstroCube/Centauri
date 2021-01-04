package net.astrocube.commons.core.cloud;

import cloud.timo.TimoCloud.api.TimoCloudAPI;
import cloud.timo.TimoCloud.api.objects.ServerGroupObject;
import cloud.timo.TimoCloud.api.objects.ServerObject;

import java.util.Optional;

public class CloudUtils {

    public static Optional<ServerObject> getServerFromGroup(String server) {

        Optional<ServerGroupObject> groupObject =
                Optional.ofNullable(TimoCloudAPI.getUniversalAPI().getServerGroup(server));

        return groupObject.flatMap(serverGroupObject -> serverGroupObject.getServers().stream().findAny());

    }

}
