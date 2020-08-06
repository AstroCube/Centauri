package net.astrocube.api.core.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public interface IdentifiableModel {

    /**
     * Will return ObjectId linked to the requested model
     * @return ObjectId as String
     */
    @JsonProperty("_id")
    String getId();

}
