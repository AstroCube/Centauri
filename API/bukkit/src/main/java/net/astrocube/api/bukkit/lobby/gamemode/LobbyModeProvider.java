package net.astrocube.api.bukkit.lobby.gamemode;

import net.astrocube.api.core.virtual.gamemode.GameMode;

import java.util.Optional;

public interface LobbyModeProvider {

    /**
     * Obtain optional {@link GameMode}
     * @return
     */
    Optional<GameMode> getRegisteredMode();

}
