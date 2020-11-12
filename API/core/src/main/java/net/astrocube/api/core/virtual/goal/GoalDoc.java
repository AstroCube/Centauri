package net.astrocube.api.core.virtual.goal;

import net.astrocube.api.core.model.Model;
import net.astrocube.api.core.model.PartialModel;

public interface GoalDoc {

    interface Partial extends PartialModel {}

    /**
     * It represents the identity of the goal.
     * And get the properties to identify this goal.
     */

    interface Identity extends Partial {

        /**
         * The id of the user representing who gain this goal
         *
         * @return The user id
         */

        String getUser();

        /**
         * Represents the objective completed
         *
         * @return The name/type of the goal objective
         */

        String getObjective();

        String getMeta();

        /**
         * The match where the goal is completed
         *
         * @return The match name
         */

        String getMatch();

        /**
         * The gamemode where the goal is completed
         *
         * @return The gamemode name
         */

        String getGamemode();

        /**
         * The sub-gamemode where the goal is completed
         *
         * @return The sub-gamemode name
         */

        String getSubGamemode();

    }

    /**
     * The object the creation of any Goal with all sub-groups
     */

    interface Complete extends Model, Identity {}
}