package net.astrocube.commons.bukkit.loader.listener.game;

import com.google.inject.Inject;
import net.astrocube.api.bukkit.server.ListenerLoader;
import net.astrocube.commons.bukkit.listener.game.management.MatchAssignationListener;
import net.astrocube.commons.bukkit.listener.game.matchmaking.MatchmakingErrorListener;
import net.astrocube.commons.bukkit.listener.game.matchmaking.MatchmakingRequestListener;
import net.astrocube.commons.bukkit.listener.game.matchmaking.MatchmakingTimeoutListener;
import org.bukkit.plugin.Plugin;

public class MatchmakingListenerLoader implements ListenerLoader {

	private @Inject Plugin plugin;

	private @Inject MatchmakingRequestListener matchmakingRequestListener;
	private @Inject MatchmakingErrorListener matchmakingErrorListener;
	private @Inject MatchmakingTimeoutListener matchmakingTimeoutListener;
	private @Inject MatchAssignationListener matchAssignationListener;

	@Override
	public void registerEvents() {
		registerEvent(
			plugin,
			matchmakingRequestListener,
			matchmakingErrorListener,
			matchmakingTimeoutListener,
			matchAssignationListener
		);
	}

}
