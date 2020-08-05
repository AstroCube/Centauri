package net.astrocube.api.core.virtual.friend;

import net.astrocube.api.core.model.Model;
import net.astrocube.api.core.model.PartialModel;

import javax.annotation.Nullable;

public interface FriendshipDoc extends Model {

    /**
     * Interface that extends partial model in order to encapsulate
     */
    interface Partial extends PartialModel {}

    /**
     * Base of a simple friendship relation
     */
    interface Relation extends Partial {

        /**
         * Will return the id of the user who sent the request
         * @return sender ID
         */
        String getSender();

        /**
         * Will return the id of the user who received the request
         * @return receiver ID
         */
        String getReceiver();

    }

    interface Force {

        /**
         * Will return id of the user who forced the friendship
         * @return admin ID
         */
        @Nullable String getIssuer();

        /**
         * Will return boolean indicating if an alert was shown
         * @return indicator boolean
         */
        boolean isAlerted();

    }

    /**
     * Creating interface that extends required and optional data without being an implicit model
     */
    interface Creation extends Relation, Force {}

    /**
     * Complete interface that implements complete model according to {@link PartialModel} schema
     */
    interface Complete extends Model, Creation {}

    /**
     * Type of friendship
     */
    enum FriendshipAction {
        CREATE,
        ACCEPT,
        FORCE
    }



}
