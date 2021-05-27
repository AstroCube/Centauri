package net.astrocube.commons.bukkit.game.match;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.beans.ConstructorProperties;

/**
 * Represents a match subscription. A user can be subscribed
 * to a match as a player, as a spectator, as an
 * assignable
 *
 * This class contains information about the match and the
 * subscription type
 */
public class MatchSubscription {

	private final String match;
	private final Type type;

	@ConstructorProperties({"match", "type"})
	public MatchSubscription(
		String match,
		Type type
	) {
		this.match = match;
		this.type = type;
	}

	/** Returns the match identifier */
	public String getMatch() {
		return match;
	}

	/** Returns the match subscription type */
	public Type getType() {
		return type;
	}

	public enum Type {
		/**
		 * In this case, the user will be found
		 * in the 'teams' match field
		 */
		@JsonProperty("Player")
		PLAYER,

		/**
		 * In this case, the user will be found
		 * in the 'spectators' match field
		 */
		@JsonProperty("Spectator")
		SPECTATOR,

		/**
		 * In this case, the user will be found
		 * in the 'pending' match field and it will
		 * be the responsible
		 */
		@JsonProperty("AssignResponsible")
		ASSIGNATION_RESPONSIBLE,

		/**
		 * In this case, the user will be found
		 * in the 'pending' match field and it will
		 * be an 'involved'
		 */
		@JsonProperty("AssignInvolved")
		ASSIGNATION_INVOLVED
	}

}
