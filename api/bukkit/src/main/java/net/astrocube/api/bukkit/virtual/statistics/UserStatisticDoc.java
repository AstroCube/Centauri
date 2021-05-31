package net.astrocube.api.bukkit.virtual.statistics;

import net.astrocube.api.core.model.Model;
import net.astrocube.api.core.model.PartialModel;

import javax.annotation.Nullable;

public interface UserStatisticDoc {


	/**
	 * Empty interface that acts as placeholder for
	 * Model Services.
	 */

	interface Partial extends PartialModel {
	}

	/**
	 * Provides methods to handle statistics.
	 */

	interface Statistic extends Partial {

		/**
		 * @return The type of statistics that is being handled by the model.
		 */

		String getType();

		/**
		 * @return An string representation of the statistic that is being handled.
		 */

		String getStatistic();

	}

	/**
	 * Provides the methods on the identity of the statistic
	 */

	interface Identity extends Partial {

		/**
		 * @return The GameMode where the statistics are being handled.
		 */

		String getGameMode();

		/**
		 * @return The Sub-GameMode where the statistics are being handled.
		 */

		@Nullable
		String getSubGameMode();

		/**
		 * Provides the responsible id of this statistic.
		 * @return The id of the responsible user.
		 */

		String getResponsible();

	}

	interface Complete extends Model.Stamped, Identity, Statistic {
	}
}