package net.astrocube.api.core.server;

import net.astrocube.api.core.virtual.gamemode.GameMode;
import net.astrocube.api.core.virtual.gamemode.SubGameMode;
import net.astrocube.api.core.virtual.server.ServerDoc;

public interface GameServerStartManager {

    String createGameServer(
            String slug,
            ServerDoc.Type type,
            String cluster,
            int maxRunning,
            int maxTotal,
            GameMode gamemode,
            SubGameMode subGamemode,
            boolean sandbox
    ) throws Exception;

}
