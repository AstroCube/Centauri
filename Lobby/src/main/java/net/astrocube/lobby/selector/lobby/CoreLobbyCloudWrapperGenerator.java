package net.astrocube.lobby.selector.lobby;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.astrocube.api.bukkit.lobby.selector.lobby.LobbyCloudWrapperGenerator;
import net.astrocube.api.core.cloud.CloudInstanceProvider;
import net.astrocube.api.core.virtual.gamemode.GameMode;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Singleton
public class CoreLobbyCloudWrapperGenerator implements LobbyCloudWrapperGenerator {

    private @Inject CloudInstanceProvider cloudInstanceProvider;

    @Override
    public List<CloudInstanceProvider.Instance> getGameModeLobbies(GameMode gameMode) {

        return cloudInstanceProvider.getGroupInstances(gameMode.getLobby()).stream()
                .sorted(Comparator.comparingInt(CloudInstanceProvider.Instance::getNumber))
                .collect(Collectors.toList());
    }

}
