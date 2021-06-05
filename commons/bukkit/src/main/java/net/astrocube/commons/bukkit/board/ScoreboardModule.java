package net.astrocube.commons.bukkit.board;

import com.google.inject.Scopes;
import me.fixeddev.inject.ProtectedModule;
import net.astrocube.api.bukkit.board.BoardProvider;
import net.astrocube.api.bukkit.board.ScoreboardManagerProvider;

public class ScoreboardModule extends ProtectedModule {

	@Override
	public void configure() {

		bind(BoardProvider.class).to(PacketBoardProvider.class).in(Scopes.SINGLETON);
		expose(BoardProvider.class);

		bind(ScoreboardManagerProvider.class).to(CoreScoreboardManagerProvider.class);
		expose(ScoreboardManagerProvider.class);
	}

}
