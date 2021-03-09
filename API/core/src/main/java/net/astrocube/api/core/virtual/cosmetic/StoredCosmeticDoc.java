package net.astrocube.api.core.virtual.cosmetic;

import net.astrocube.api.core.model.Model;
import net.astrocube.api.core.model.PartialModel;

import javax.annotation.Nullable;

public interface StoredCosmeticDoc {

    interface Partial extends PartialModel {}

    interface Identity extends Partial {

        /**
         * The gamemode where the cosmetic was purchased or earned
         *
         * @return The gamemode type
         */

        String getGameMode();

        /**
         * The optional sub-gamemode where the cosmetic was purchased or earned
         *
         * @return The optional sub-gamemode
         */

        @Nullable
        String getSubGameMode();

        /**
         * The responsible user of this cosmetic
         *
         * @return The responsible user id
         */

        String getResponsible();
    }

    /**
     * Represents a cosmetic can be earned or purchased
     */

    interface EarnableObject {

        /**
         * The earned cosmetic type, like a kit, perk or anything that you want
         *
         * @return The earned cosmetic type
         */

        String getType();

        /**
         * The contents of the earned cosmetic type
         *
         * @return The cosmetic type contents in an object to generalize the usage
         */

        Object getStored();
    }

    interface Complete extends Model.Stamped, Identity, EarnableObject {}
}