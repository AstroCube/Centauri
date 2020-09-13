package net.astrocube.api.bukkit.game;

import net.astrocube.api.bukkit.game.exception.GameControlException;
import net.astrocube.api.bukkit.lobby.event.LobbyJoinEvent;
import net.astrocube.api.core.virtual.gamemode.GameMode;
import net.astrocube.api.core.virtual.gamemode.SubGameMode;
import net.astrocube.api.core.virtual.server.ServerDoc;

public interface GameControlPair {

    /**
     * Validate if configuration values are correctly pairing with
     * the data provided by {@link LobbyJoinEvent}.
     * @param gameMode to be checked
     * @param subGameMode to be checked
     *
     * @throws GameControlException when provided values do not correspond with
     * the configuration ones or {@link ServerDoc.Type} is not GAME.
     */
    void validatePair(GameMode gameMode, SubGameMode subGameMode) throws GameControlException;



}
