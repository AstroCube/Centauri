package net.astrocube.api.core.server;

import net.astrocube.api.core.virtual.server.ServerDoc;

public interface ServerStartManager {

    String createServer(
            String slug,
            ServerDoc.Type type,
            String cluster
    ) throws Exception;

}
