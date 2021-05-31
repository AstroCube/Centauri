package net.astrocube.lobby.gamemode;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.astrocube.api.bukkit.lobby.gamemode.LobbyModeProvider;
import net.astrocube.api.core.service.find.FindService;
import net.astrocube.api.core.virtual.gamemode.GameMode;
import org.bukkit.plugin.Plugin;

import java.util.Optional;
import java.util.logging.Level;

@Singleton
public class CoreLobbyModeProvider implements LobbyModeProvider {

	private @Inject FindService<GameMode> findService;
	private @Inject Plugin plugin;

	@Override
	public Optional<GameMode> getRegisteredMode() {

		try {
			return Optional.of(this.findService.findSync(plugin.getConfig().getString("gamemode")));
		} catch (Exception ex) {
			plugin.getLogger().log(Level.WARNING,
				"Could not provide correctly GameMode. This may cause some functionalities to fail.", ex
			);
		}

		return Optional.empty();
	}

}
