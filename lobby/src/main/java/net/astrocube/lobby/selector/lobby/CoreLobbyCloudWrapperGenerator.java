package net.astrocube.lobby.selector.lobby;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.astrocube.api.bukkit.lobby.selector.lobby.LobbyCloudWrapperGenerator;
import net.astrocube.api.core.cloud.CloudInstanceProvider;
import net.astrocube.api.core.virtual.gamemode.GameMode;

import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Singleton
public class CoreLobbyCloudWrapperGenerator implements LobbyCloudWrapperGenerator {

	private @Inject CloudInstanceProvider cloudInstanceProvider;

	private static final Pattern FIRST_NUMBER_PATTERN = Pattern.compile("[^0-9]*([0-9]+).*");

	@Override
	public List<CloudInstanceProvider.Instance> getGameModeLobbies(GameMode gameMode) {
		return cloudInstanceProvider.getGroupInstances(gameMode.getLobby()).stream()
			.sorted(Comparator.comparingInt(value -> {
				Matcher matcher = FIRST_NUMBER_PATTERN.matcher(value.getName());
				return matcher.find() ? Integer.parseInt(matcher.group()) : 1;
			}))
			.collect(Collectors.toList());
	}
}