package net.astrocube.lobby.selector.lobby;

import com.google.inject.Singleton;
import net.astrocube.api.bukkit.lobby.selector.lobby.LobbyCloudWrapperGenerator;
import net.astrocube.api.bukkit.lobby.selector.lobby.LobbySelectorWrapper;
import net.astrocube.api.core.virtual.gamemode.GameMode;
import org.bukkit.Bukkit;

import java.util.Arrays;
import java.util.List;

@Singleton
public class CoreLobbyCloudWrapperGenerator implements LobbyCloudWrapperGenerator {

    @Override
    public List<LobbySelectorWrapper> getGameModeLobbies(GameMode gameMode) {
        return Arrays.asList(
                generateTestLobbies(1, 5, 1, false),
                generateTestLobbies(2, 2, 2, false),
                generateTestLobbies(3, 5, 3, true),
                generateTestLobbies(3, 3, 4, true)
        );
    }

    private LobbySelectorWrapper generateTestLobbies(int connected, int max, int pos, boolean same) {
        return new LobbySelectorWrapper() {
            @Override
            public String getName() {
                return same ? Bukkit.getServerName() : "Test Lobby";
            }

            @Override
            public int getConnected() {
                return connected;
            }

            @Override
            public int getMax() {
                return max;
            }

            @Override
            public boolean isFull() {
                return connected >= max;
            }

            @Override
            public int getNumber() {
                return pos;
            }
        };
    }

}
