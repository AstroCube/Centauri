package net.astrocube.api.core.virtual.gamemode;

import net.astrocube.api.core.model.Model;
import net.astrocube.api.core.model.PartialModel;

import java.util.Set;

public interface GameModeDoc {

	interface Partial extends PartialModel {
	}

	interface Identity extends Partial {

		/**
		 * Will return the GameMode name
		 * @return string with name
		 */
		String getName();

		/**
		 * WIll return the SubGameMode object Set
		 * @return subGameMode types
		 */
		Set<SubGameMode> getSubTypes();

	}

	interface Navigator {

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

	}

	interface Complete extends Identity, Navigator, Model.Stamped {
	}

}
