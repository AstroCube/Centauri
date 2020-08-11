package net.astrocube.api.core.virtual.session;

import com.fasterxml.jackson.annotation.JsonProperty;
import net.astrocube.api.core.model.Model;
import net.astrocube.api.core.model.PartialModel;
import net.astrocube.api.core.virtual.user.User;

import javax.annotation.Nullable;

public interface SessionValidateDoc {

    interface Partial extends PartialModel {}

    interface Complete extends Model {

        /**
         * @return user related to the session. Can be the authentic user or user that infringes policy.
         */
        @JsonProperty("user")
        User getUser();

        /**
         * @return if user is correctly registered or his account can be claimed.
         */
        @JsonProperty("registered")
        boolean isRegistered();

        /**
         * @return multi account procedure.
         */
        @JsonProperty("multiAccount")
        boolean isMultiAccount();

    }

    interface Request extends Partial {

        @JsonProperty("username")
        String getUser();

        @JsonProperty("address")
        String getAddress();

    }

    interface ServerSwitch extends Partial {

        @JsonProperty("user")
        String getUser();

        @JsonProperty("server")
        String getServer();

        @JsonProperty("lobby")
        @Nullable String getLobby();

    }

}
