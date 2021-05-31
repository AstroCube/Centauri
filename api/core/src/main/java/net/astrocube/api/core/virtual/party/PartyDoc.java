package net.astrocube.api.core.virtual.party;

import net.astrocube.api.core.model.Model;
import net.astrocube.api.core.model.PartialModel;

import java.util.Set;

public interface PartyDoc extends Model {

	/** Interface that extends partial model in order to encapsulate */
	interface Partial extends PartialModel {
	}

	interface Creation extends Partial {

		/** @return The party leader id */
		String getLeader();

		/**
		 * @return A mutable set containing
		 * the member ids of this party
		 */
		Set<String> getMembers();

	}

	/** Complete interface that implements complete model according to {@link PartialModel} schema */
	interface Complete extends Model.Stamped, Creation {

		void setLeader(String leader);

	}

}
