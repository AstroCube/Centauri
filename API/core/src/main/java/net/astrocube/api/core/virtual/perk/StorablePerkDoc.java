package net.astrocube.api.core.virtual.perk;

import net.astrocube.api.core.model.Model;
import net.astrocube.api.core.model.PartialModel;

import javax.annotation.Nullable;

public interface StorablePerkDoc {

    interface Partial extends PartialModel {}

    interface Identity extends Partial {

        /**
         * The gamemode where the perk was purchased or earned
         *
         * @return The gamemode type
         */
        String getGameMode();

        /**
         * The optional sub-gamemode where the perk was purchased or earned
         *
         * @return The optional sub-gamemode
         */

        @Nullable
        String getSubGameMode();

        /**
         * The responsible user of this perk
         *
         * @return The responsible user id
         */

        String getResponsible();
    }

    /**
     * Represents a perk can be earned or purchased
     */
    interface StorableManifest {

        /**
         * The earned perk type, like a kit, game enhancement, or anything that you want
         *
         * @return The earned perk type
         */
        String getType();

        /**
         * The contents of the earned perk type
         *
         * @return The perk type contents in an object to generalize the usage
         */
        Object getStored();
    }

    interface Complete extends Model.Stamped, Identity, StorableManifest {}
}