package net.astrocube.api.core.player;

import net.astrocube.api.core.message.Message;

public interface ProxyKickRequest extends Message {

    /**
     * @return name of the player to kick
     */
    String getName();

    /**
     * @return reason of the punishment
     */
    String getReason();

}
