package net.astrocube.api.core.cloud;

import lombok.Data;

import java.util.Set;

public interface CloudInstanceProvider {

	Set<CloudInstanceProvider.Instance> getGroupInstances(String name);

	boolean isAvailable(String slug);

	@Data
	class Instance {

		/**
		 * The name of the wrapper
		 */
		private final String name;

		/**
		 * Actual connected users
		 */
		private final int connected;

		/**
		 * Max allowed users at a certain
		 * lobby
		 */
		private final int max;

		/**
		 * Check if lobby is full
		 */
		private final boolean full;

		/**
		 * Lobby number
		 */
		private final int number;

	}

}
