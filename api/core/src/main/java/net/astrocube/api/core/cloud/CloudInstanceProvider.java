package net.astrocube.api.core.cloud;

import java.util.Set;

public interface CloudInstanceProvider {

	Set<CloudInstanceProvider.Instance> getGroupInstances(String name);

	boolean isAvailable(String slug);

	interface Instance {

		/**
		 * Obtain name of the wrapper
		 * @return string containing name
		 */
		String getName();

		/**
		 * Obtain actual connected users
		 * @return indicator of connected users
		 */
		int getConnected();

		/**
		 * Obtain max allowed users at a certain lobby
		 * @return indicator of max users
		 */
		int getMax();

		/**
		 * Check if lobby is full
		 * @return indicator of connected users
		 */
		boolean isFull();

		/**
		 * Obtain lobby number
		 * @return lobby number to be shown
		 */
		int getNumber();

	}

}
