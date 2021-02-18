package net.astrocube.api.bukkit.game.spectator;

import net.astrocube.api.core.message.Message;
import net.astrocube.api.core.virtual.gamemode.GameMode;
import net.astrocube.api.core.virtual.gamemode.SubGameMode;

public interface SpectateRequest extends Message {

    /**
     * @return {@link GameMode} id of the request.
     */
    String getMode();

    /**
     * @return {@link SubGameMode} id of the request.
     */
    String getSubMode();

    /**
     * @return requester id
     */
    String getRequester();

    /**
     * @return match id
     */
    String getMatch();

    /**
     * @return server id
     */
    String getServer();

    enum State {
        SUCCESS, ERROR, VOIDED
    }

}
