package net.astrocube.lobby.board;

import me.fixeddev.inject.ProtectedModule;
import net.astrocube.api.bukkit.lobby.board.ScoreboardProcessor;

public class ScoreboardModule extends ProtectedModule {

	@Override
	public void configure() {
		bind(ScoreboardProcessor.class).to(CoreScoreboardProcessor.class);
	}

}
