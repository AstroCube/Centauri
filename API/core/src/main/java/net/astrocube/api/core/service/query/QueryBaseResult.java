package net.astrocube.api.core.service.query;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.node.ArrayNode;
import net.astrocube.api.core.message.Message;

public interface QueryBaseResult extends Message {

	/**
	 * Complete collection of data that will return as part of pagination
	 * @return found models
	 */
	@JsonProperty("data")
	ArrayNode getFoundModels();

}