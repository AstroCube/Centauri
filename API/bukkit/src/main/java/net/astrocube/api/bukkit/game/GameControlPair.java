package net.astrocube.api.bukkit.game;

import net.astrocube.api.bukkit.game.event.game.GameModePairEvent;
import net.astrocube.api.bukkit.game.exception.GameControlException;
import net.astrocube.api.core.virtual.gamemode.GameMode;
import net.astrocube.api.core.virtual.gamemode.SubGameMode;
import net.astrocube.api.core.virtual.server.ServerDoc;

public interface GameControlPair {

    /**
     * Validate if configuration values are correctly pairing with
     * the data provided by {@link GameModePairEvent}.
     * @param gameMode to be checked
     * @param subGameMode to be checked
     *
     * @throws GameControlException when provided values do not correspond with
     * the configuration ones or {@link ServerDoc.Type} is not GAME.
     */
    void validatePair(String gameMode, String subGameMode) throws Exception;

    /**
     * Schedules a task that will close prematurely the server if
     * no game generate a successful pairing.
     * @throws GameControlException when {@link ServerDoc.Type} is not GAME.
     */
    void enablePairing() throws GameControlException;

    /**
     * Check if server actually is paired with any plugin in order to allow
     * certain Bukkit events to occur that may produce exceptions.
     * @return boolean checking if server paired
     */
    boolean isPaired();

}
