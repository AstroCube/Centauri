package net.astrocube.commons.bukkit.game.match.control;

import com.google.inject.Inject;
import net.astrocube.api.bukkit.game.exception.GameControlException;
import net.astrocube.api.bukkit.game.match.control.MatchScheduler;
import net.astrocube.api.bukkit.game.matchmaking.MatchmakingRequest;
import net.astrocube.api.bukkit.virtual.game.match.MatchDoc;
import net.astrocube.api.core.server.ServerService;
import net.astrocube.api.core.virtual.server.Server;

import java.util.Optional;

public class CoreMatchScheduler implements MatchScheduler {

    private @Inject ServerService serverService;

    @Override
    public void schedule(MatchmakingRequest request) throws Exception {

        Server actual = serverService.getActual();

        if (
                (request != null && actual != null) &&
                        ac tual.getGameMode().equalsIgnoreCase(request.getGameMode()) ||
                        actual.getSubGameMode().equalsIgnoreCase(request.getSubGameMode())) {
            throw new GameControlException("Illegal match scheduling. Server not paired for this gamemode");
        }


        MatchDoc.Partial match = new MatchDoc.Identity() {

            private MatchDoc.Status status = MatchDoc.Status.LOBBY;

            @Override
            public String getMap() {
                return request == null ? "" :
                        request.getMap().isPresent() ? request.getMap().get() : "";
            }

            @Override
            public String getServer() {
                return actual.getId();
            }

            @Override
            public MatchDoc.Status getStatus() {
                return status;
            }

            @Override
            public void setStatus(MatchDoc.Status status) {
                this.status = status;
            }

            @Override
            public String getGameMode() {
                return actual.getGameMode();
            }

            @Override
            public String getSubMode() {
                return actual.getSubGameMode();
            }

        };



    }

    @Override
    public void schedule() {
    }
}
