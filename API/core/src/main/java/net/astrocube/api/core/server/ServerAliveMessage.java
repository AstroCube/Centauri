package net.astrocube.api.core.server;

import net.astrocube.api.core.message.Message;

public interface ServerAliveMessage extends Message {

    /**
     * @return server that will be requested / used.
     */
    String getServer();

    /**
     * @return action to be broadcasted / received.
     */
    Action getAction();

    enum Action {
        REQUEST, CONFIRM
    }

}
