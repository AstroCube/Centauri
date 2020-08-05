package net.astrocube.api.core.virtual.gamemode;

import net.astrocube.api.core.model.Document;

public interface SubGameMode extends Document {

    /**
     * Will return the name of the SubGameMode
     * @return string containing name
     */
    String getName();

    /**
     * Will return boolean indicating if allows map selection
     * @return boolean with indicator
     */
    boolean hasSelectableMap();

    /**
     * Minimum players to allow normal game start
     * @return integer with min players
     */
    int getMinPlayers();

    /**
     * Maximum players to allow at a certain SubGameMode
     * @return integer with max players
     */
    int getMaxPlayers();

    /**
     * Permission that must be obtained to access the SubGameMode
     * @return string with located permission
     */
    String getPermission();

    /**
     * Cloud group indicator to redirect users at matchmaking
     * @return string with group
     */
    String getGroup();


}
