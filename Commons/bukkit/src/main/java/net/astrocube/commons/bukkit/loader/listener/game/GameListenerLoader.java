package net.astrocube.commons.bukkit.loader.listener.game;

import com.google.inject.Inject;
import net.astrocube.api.bukkit.server.ListenerLoader;
import net.astrocube.commons.bukkit.listener.game.management.GameChatListener;
import net.astrocube.commons.bukkit.listener.game.management.GameModePairListener;
import net.astrocube.commons.bukkit.listener.game.management.GameTimerOutListener;
import net.astrocube.commons.bukkit.listener.game.session.GameServerJoinListener;
import org.bukkit.plugin.Plugin;

public class GameListenerLoader implements ListenerLoader {

    private @Inject Plugin plugin;

    private @Inject GameModePairListener gameModePairListener;
    private @Inject GameTimerOutListener gameTimerOutListener;
    private @Inject GameServerJoinListener gameServerJoinListener;
    private @Inject GameChatListener gameChatListener;

    @Override
    public void registerEvents() {
        registerEvent(
                plugin,
                gameModePairListener,
                gameTimerOutListener,
                gameServerJoinListener,
                gameChatListener
        );
    }

}
