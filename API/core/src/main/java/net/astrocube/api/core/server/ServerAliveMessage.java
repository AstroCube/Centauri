package net.astrocube.api.core.server;

import com.fasterxml.jackson.annotation.JsonProperty;
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
        @JsonProperty("Request")
        REQUEST,
        @JsonProperty("Confirm")
        CONFIRM
    }

}
