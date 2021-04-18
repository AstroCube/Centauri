package net.astrocube.api.core.session;

import com.fasterxml.jackson.annotation.JsonProperty;
import net.astrocube.api.core.message.Message;
import net.astrocube.api.core.message.MessageDefaults;

@MessageDefaults.ChannelName("session-user-pingback")
public interface SessionPingMessage extends Message {

    /**
     * @return pinged user
     */
    String getUser();

    /**
     * @return to proceed
     */
    Action getAction();

    enum Action {
        @JsonProperty("Request")
        REQUEST,
        @JsonProperty("Response")
        RESPONSE,
        @JsonProperty("Disconnect")
        DISCONNECT
    }

}
