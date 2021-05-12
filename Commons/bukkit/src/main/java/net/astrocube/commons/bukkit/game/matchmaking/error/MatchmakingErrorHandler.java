package net.astrocube.commons.bukkit.game.matchmaking.error;

import net.astrocube.api.bukkit.game.event.matchmaking.MatchmakingErrorEvent;
import net.astrocube.api.bukkit.game.matchmaking.error.MatchmakingError;
import net.astrocube.api.core.message.MessageHandler;
import net.astrocube.api.core.message.Metadata;
import org.bukkit.Bukkit;

public class MatchmakingErrorHandler implements MessageHandler<MatchmakingError> {

	@Override
	public Class<MatchmakingError> type() {
		return MatchmakingError.class;
	}

	@Override
	public void handleDelivery(MatchmakingError message, Metadata properties) {
		Bukkit.getPluginManager().callEvent(new MatchmakingErrorEvent(message));
	}

}
