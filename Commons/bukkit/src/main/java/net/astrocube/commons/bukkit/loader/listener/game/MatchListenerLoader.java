package net.astrocube.commons.bukkit.loader.listener.game;

import com.google.inject.Inject;
import net.astrocube.api.bukkit.server.ListenerLoader;
import net.astrocube.commons.bukkit.listener.game.management.MatchControlSanitizeListener;
import net.astrocube.commons.bukkit.listener.game.management.MatchFinishListener;
import net.astrocube.commons.bukkit.listener.game.management.MatchInvalidationListener;
import net.astrocube.commons.bukkit.listener.game.management.MatchStartListener;
import org.bukkit.plugin.Plugin;

public class MatchListenerLoader implements ListenerLoader {

    private @Inject Plugin plugin;

    private @Inject MatchControlSanitizeListener matchControlSanitizeListener;
    private @Inject MatchInvalidationListener matchInvalidationListener;
    private @Inject MatchStartListener matchStartListener;
    private @Inject MatchFinishListener matchFinishListener;

    @Override
    public void registerEvents() {
        registerEvent(
                plugin,
                matchControlSanitizeListener,
                matchInvalidationListener,
                matchStartListener,
                matchFinishListener
        );
    }

}
