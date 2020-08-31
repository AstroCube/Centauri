package net.astrocube.api.core.virtual.gamemode;

import net.astrocube.api.core.model.Model;
import net.astrocube.api.core.model.ModelProperties;

import javax.annotation.Nullable;
import java.util.Set;

public interface GameModeDoc extends Model.Stamped {

    /**
     * Will return the GameMode name
     * @return string with name
     */
    String getName();

    /**
     * Will return the Cloud group name linked to the GameMode
     * @return string with group name
     */
    String getLobby();

    /**
     * Will return th object to be displayed at navigator
     * @return string of the navigator
     */
    String getNavigator();

    /**
     * Will return the slot where will be displayed the GameMode
     * @return integer with slot
     */
    int getSlot();

    /**
     * WIll return the SubGameMode object Set
     * @return subGameMode types
     */
    @Nullable Set<SubGameMode> getSubTypes();

    interface Partial extends GameModeDoc {}

}
