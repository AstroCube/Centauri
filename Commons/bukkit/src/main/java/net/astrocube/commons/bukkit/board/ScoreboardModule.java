package net.astrocube.commons.bukkit.board;

import com.google.inject.Provides;
import me.fixeddev.inject.ProtectedModule;
import org.bukkit.plugin.Plugin;
import team.unnamed.uboard.ScoreboardManager;
import team.unnamed.uboard.SimpleScoreboardManager;

public class ScoreboardModule extends ProtectedModule {

    @Provides
    public ScoreboardManager getManager(Plugin plugin) {
        return new SimpleScoreboardManager(plugin);
    }

}
