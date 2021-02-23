package net.astrocube.commons.bukkit.loader.listener;

import com.google.inject.Inject;
import net.astrocube.api.bukkit.server.ListenerLoader;
import net.astrocube.commons.bukkit.listener.freeze.PlayerMoveListener;
import net.astrocube.commons.bukkit.listener.freeze.PlayerQuitListener;
import net.astrocube.commons.bukkit.listener.game.spectator.PlayerDamageListener;
import org.bukkit.plugin.Plugin;
import team.unnamed.gui.core.GUIListeners;

public class InteractionListenerLoader implements ListenerLoader {

    private @Inject Plugin plugin;

    private @Inject PlayerDamageListener playerDamageListener;
    private @Inject PlayerMoveListener playerMoveListener;
    private @Inject PlayerQuitListener playerQuitListener;

    private @Inject GUIListeners guiListeners;

    @Override
    public void registerEvents() {
        registerEvent(
                plugin,
                playerDamageListener,
                playerMoveListener,
                playerQuitListener,
                guiListeners
        );
    }

}
