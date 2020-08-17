package net.astrocube.api.core.virtual.punishment;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import net.astrocube.api.core.model.Model;
import net.astrocube.api.core.model.PartialModel;
import net.astrocube.api.core.virtual.user.User;

public interface PunishmentDoc {

    interface Partial extends PartialModel {}

    interface Identity extends Partial {

        User getIssuer();

        User getPunished();

        Type getType();

        enum Type {
            @JsonProperty("Ban") BAN,
            @JsonProperty("Kick") KICK,
            @JsonProperty("Warn") WARN
        }

    }

    interface Options extends Identity {

        boolean isAutomatic();

        boolean isSilent();

        String getReason();

    }

    interface Expiration extends Identity {

        long getExpiration();

        @JsonIgnore
        default boolean isPermanent() {
            return getExpiration() < 0;
        }

    }

    interface Creation extends Identity, Options, Expiration {}

    interface Complete extends Model.Stamped, Creation {

        boolean isAppealed();

        boolean isActive();

    }

}