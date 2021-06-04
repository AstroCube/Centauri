package net.astrocube.api.core.virtual.server;

import com.fasterxml.jackson.annotation.JsonProperty;
import net.astrocube.api.core.model.Model;
import net.astrocube.api.core.model.PartialModel;

import javax.annotation.Nullable;

public interface ServerDoc {

	/**
	 * Interface that extends partial model in order to encapsulate
	 */
	interface Partial extends PartialModel {
	}

	/**
	 * Base identity that must be provided in order to successful server creation
	 */
	interface Identity extends Partial {

		/**
		 * Cloud indicator as slug string
		 * @return slug of the server
		 */
		String getSlug();

		/**
		 * @return if the server is in sandbox mode
		 */
		boolean isSandbox();

		/**
		 * Type of the server that should be resolved
		 * @return server type
		 */
		@JsonProperty("type")
		Type getServerType();

		/**
		 * Cluster of the server that will authorize creation
		 * @return cluster string
		 */
		String getCluster();

	}

	/**
	 * Will provide base data for GAME server creation
	 */
	interface GameBase {

		/**
		 * GameMode string to be populated later with providers
		 * @return GameMode database ID
		 */
		@Nullable
		String getGameMode();

		/**
		 * SubGameMode string to be populated later with providers
		 * @return SubGameMode database ID
		 */
		@Nullable
		String getSubGameMode();

		/**
		 * Max running games in simultaneous
		 * @return integer indicating max running
		 */
		int getMaxRunning();

		/**
		 * Max total games that a server is allowed to run
		 * @return max total of the server
		 */
		int getMaxTotal();

	}

	/**
	 * Creating interface that extends required and optional data without being an implicit model
	 */
	interface Creation extends Identity, GameBase {
	}

	/**
	 * Complete interface that implements complete model according to {@link PartialModel} schema
	 */
	interface Complete extends Model.Stamped, Identity, GameBase {
	}

	/**
	 * Server Types to be used
	 */
	enum Type {
		@JsonProperty("Lobby")
		LOBBY,
		@JsonProperty("Game")
		GAME,
		@JsonProperty("Special")
		SPECIAL,
		@JsonProperty("Bungee")
		BUNGEE
	}

}