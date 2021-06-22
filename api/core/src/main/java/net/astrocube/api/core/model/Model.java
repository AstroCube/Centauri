package net.astrocube.api.core.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;


public interface Model extends PartialModel {

	/**
	 * Will return ObjectId linked to the requested model
	 * @return ObjectId as String
	 */
	@JsonProperty("_id")
	String getId();

	interface Stamped extends Model {

		/**
		 * Instant where the model was exactly created at
		 * @return created at instant
		 */
		LocalDateTime getCreatedAt();

		/**
		 * Instant where the model was exactly updated at
		 * @return updated at instant
		 */
		LocalDateTime getUpdatedAt();

	}

}