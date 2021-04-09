package net.astrocube.commons.core.server;

import com.google.inject.Inject;
import net.astrocube.api.core.server.GameServerStartManager;
import net.astrocube.api.core.server.ServerService;
import net.astrocube.api.core.virtual.gamemode.GameMode;
import net.astrocube.api.core.virtual.gamemode.SubGameMode;
import net.astrocube.api.core.virtual.server.ServerDoc;

public class CentauriGameStartManager implements GameServerStartManager {

    private @Inject ServerService serverService;

    @Override
    public String createGameServer(String slug, ServerDoc.Type type, String cluster, int maxRunning, int maxTotal, GameMode gamemode, SubGameMode subGamemode) throws Exception {

        ServerDoc.Creation gameCreation = new ServerDoc.Creation() {
            @Override
            public String getGameMode() {
                return gamemode.getId();
            }

            @Override
            public String getSubGameMode() {
                return subGamemode.getId();
            }

            @Override
            public int getMaxRunning() {
                return maxRunning;
            }

            @Override
            public int getMaxTotal() {
                return maxTotal;
            }

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

        return serverService.connect(() -> gameCreation);
    }

}
