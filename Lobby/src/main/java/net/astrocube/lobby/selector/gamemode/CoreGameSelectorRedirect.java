package net.astrocube.lobby.selector.gamemode;

import com.google.inject.Singleton;
import net.astrocube.api.bukkit.lobby.selector.gamemode.GameSelectorRedirect;
import net.astrocube.api.core.virtual.gamemode.GameMode;
import org.bukkit.entity.Player;

@Singleton
public class CoreGameSelectorRedirect implements GameSelectorRedirect {

    @Override
    public void redirectPlayer(GameMode gameMode, Player player) {
        /*
            TODO:
             - Redirect player with Cloud Integration
         */
    }

}
