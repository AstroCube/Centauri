package net.astrocube.api.bukkit.lobby.selector.npc;

import net.astrocube.api.core.virtual.gamemode.SubGameMode;
import org.bukkit.GameMode;

public interface LobbyNPCSelector {

    /**
     * @return {@link GameMode} id of the selector
     */
    String getMode();

    /**
     * @return {@link SubGameMode} id of the selector
     */
    String getSubMode();

    /**
     * @return skin value
     */
    String getValue();

    /**
     * @return skin signature
     */
    String getSignature();

    /**
     * @return X coordinate to spawn
     */
    float getX();

    /**
     * @return Y coordinate to spawn
     */
    float getY();

    /**
     * @return Z coordinate to spawn
     */
    float getZ();

    /**
     * @return Yaw coordinate to spawn
     */
    int getYaw();

    /**
     * @return Pitch coordinate to spawn
     */
    int getPitch();

}
