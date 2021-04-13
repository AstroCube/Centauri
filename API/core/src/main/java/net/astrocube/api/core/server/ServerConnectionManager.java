package net.astrocube.api.core.server;

import net.astrocube.api.core.virtual.server.ServerDoc;

public interface ServerConnectionManager {

    String startConnection(
            String slug,
            ServerDoc.Type type,
            String cluster,
            boolean sandbox
    ) throws Exception;

    void endConnection() throws Exception;

}
