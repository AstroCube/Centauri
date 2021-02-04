package net.astrocube.api.core.virtual.punishment;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import net.astrocube.api.core.model.Model;
import net.astrocube.api.core.model.PartialModel;
import org.joda.time.DateTime;

public interface PunishmentDoc extends Model {

    interface Partial extends PartialModel {}

    /**
     * It represents the punishment identity. Getting properties of this.
     */
    interface Identity extends Partial {

        /**
         * Gets the issuer that has created the punishment.
         *
         * @return The issuer that has created the punishment.
         */
        String getIssuer();

        /**
         * Gets the id of the player punished.
         *
         * @return The if of the player punished.
         */
        String getPunished();

        /**
         * @return The {@linkplain Type} of the punishment.
         */
        Type getType();

        /**
         * It represents the type of punishment that can be.
         */
        @Getter
        @AllArgsConstructor
        enum Type {
            @JsonProperty("Ban") BAN,
            @JsonProperty("Kick") KICK,
            @JsonProperty("Warn") WARN
        }

    }

    /**
     * Represents the possible options that a punishment may have.
     */
    interface Options extends Identity {

        /**
         * @return Boolean indicating if a punishment was issued automatically.
         */
        boolean isAutomatic();

        /**
         * @return Boolean indicating if a punishment was issued silently
         */
        boolean isSilent();

        /**
         * @return The reason of the punishment.
         */
        String getReason();

    }

    /**
     * It represents the times of the punishment, when it was created,
     * when it expires and the last time it was updated.
     *
     * See {@linkplain Model.Stamped}.
     */
    interface Expiration extends Identity, Model.Stamped {

        /**
         * @return The data time that the punishment will expire.
         */
        DateTime getExpiration();

    }

    /**
     * It represents the creation of any punishment.
     */
    interface Creation extends Identity, Options, Expiration {}

    /**
     * It represents the final creation of any punishment, when the
     * punishment expires.
     */
    interface Complete extends Model.Stamped, Creation {

        /**
         * @return Boolean indicating if the punishment was appealed.
         */
        boolean isAppealed();

        /**
         * @return Boolean indicating if the punishment stay active.
         */
        boolean isActive();

    }

}