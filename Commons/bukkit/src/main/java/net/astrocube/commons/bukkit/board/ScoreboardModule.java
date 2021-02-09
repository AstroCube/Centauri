package net.astrocube.commons.bukkit.board;

import me.fixeddev.inject.ProtectedModule;
import net.astrocube.api.bukkit.board.ScoreboardManagerProvider;
import team.unnamed.uboard.ScoreboardManager;

public class ScoreboardModule extends ProtectedModule {

    @Override
    public void configure() {
        bind(ScoreboardManagerProvider.class).to(CoreScoreboardManagerProvider.class);
        bind(ScoreboardManagerProvider.class);
    }

}
