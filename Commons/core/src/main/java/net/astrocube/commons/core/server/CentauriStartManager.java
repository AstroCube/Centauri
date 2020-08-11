package net.astrocube.commons.core.server;

import com.google.inject.Inject;
import net.astrocube.api.core.concurrent.AsyncResponse;
import net.astrocube.api.core.server.ServerService;
import net.astrocube.api.core.server.ServerStartManager;
import net.astrocube.api.core.virtual.server.ServerDoc;

public class CentauriStartManager implements ServerStartManager {

    private final ServerService serverService;

    @Inject
    public CentauriStartManager(ServerService serverService) {
        this.serverService = serverService;
    }

    @Override
    public String createServer(String slug, ServerDoc.Type type, String cluster) throws Exception {
        ServerDoc.Identity identity = new ServerDoc.Identity() {
            @Override
            public String getSlug() {
                return slug;
            }

            @Override
            public ServerDoc.Type getServerType() {
                return type;
            }

            @Override
            public String getCluster() {
                return cluster;
            }
        };
        return this.serverService.connect(() -> identity);
    }

}
