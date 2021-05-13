package net.astrocube.commons.bukkit.authentication.radio;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.astrocube.api.bukkit.authentication.radio.AuthenticationRadio;
import net.astrocube.api.bukkit.authentication.radio.AuthenticationSongLoader;
import org.bukkit.entity.Player;

@Singleton
public class CoreAuthenticationRadio implements AuthenticationRadio {

	private @Inject AuthenticationSongLoader authenticationSongLoader;

	@Override
	public void addPlayer(Player player) {

		if (!authenticationSongLoader.getBroadcaster().isPlaying()) {
			authenticationSongLoader.getBroadcaster().setPlaying(true);
		}

		authenticationSongLoader.getBroadcaster().addAudienceMember(player);

	}

	@Override
	public void removePlayer(Player player) {
		authenticationSongLoader.getBroadcaster().removeAudienceMember(player);
	}

}
