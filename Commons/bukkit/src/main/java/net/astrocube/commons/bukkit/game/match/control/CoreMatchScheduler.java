package net.astrocube.commons.bukkit.game.match.control;

import net.astrocube.api.bukkit.game.match.control.MatchScheduler;
import net.astrocube.api.bukkit.game.matchmaking.MatchmakingRequest;
import net.astrocube.api.bukkit.virtual.game.match.MatchDoc;

public class CoreMatchScheduler implements MatchScheduler {

    @Override
    public void schedule(MatchmakingRequest request) {

        MatchDoc.Partial match = new MatchDoc.Identity() {

            @Override
            public String getMap() {
                return null;
            }

            @Override
            public String getServer() {
                return null;
            }

            @Override
            public MatchDoc.Status getStatus() {
                return null;
            }

            @Override
            public void setStatus(MatchDoc.Status status) {

            }

            @Override
            public String getGameMode() {
                return null;
            }

            @Override
            public String getSubMode() {
                return null;
            }

        };

    }

    @Override
    public void schedule() {
    }
}
