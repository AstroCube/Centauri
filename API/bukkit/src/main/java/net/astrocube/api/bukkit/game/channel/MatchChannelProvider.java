package net.astrocube.api.bukkit.game.channel;

import net.astrocube.api.bukkit.virtual.channel.ChatChannel;
import net.astrocube.api.bukkit.virtual.game.match.Match;

import java.util.Optional;

public interface MatchChannelProvider {

	/**
	 * Register a channel at the database for a {@link Match}.
	 * @param match to create
	 */
	void createChannel(String match) throws Exception;

	/**
	 * Retrieve a channel from the database.
	 * @param match to find
	 * @return optional of found channel
	 */
	Optional<ChatChannel> retrieveChannel(String match);

	/**
	 * Unlink channel from database.
	 * @param match to remove
	 */
	void unlinkChannel(String match);

}
