package net.astrocube.api.bukkit.authentication.radio;

import net.astrocube.apollo.api.Broadcaster;

public interface AuthenticationSongLoader {

	/**
	 * Generates all broadcaster music from the songs folder
	 */
	void generateBroadcaster();

	/**
	 * @return broadcaster
	 */
	Broadcaster getBroadcaster();

}
