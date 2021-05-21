package net.astrocube.commons.bukkit.game.matchmaking.error;

import net.astrocube.api.bukkit.game.event.matchmaking.MatchmakingErrorEvent;
import net.astrocube.api.bukkit.game.matchmaking.error.MatchmakingError;
import net.astrocube.api.core.message.MessageListener;
import net.astrocube.api.core.message.MessageMetadata;
import org.bukkit.Bukkit;

public class MatchmakingErrorHandler implements MessageListener<MatchmakingError> {

	@Override
	public void handleDelivery(MatchmakingError message, MessageMetadata properties) {
		Bukkit.getPluginManager().callEvent(new MatchmakingErrorEvent(message));
	}

}
