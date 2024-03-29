package net.astrocube.api.core.service.query;

import com.fasterxml.jackson.annotation.JsonProperty;
import net.astrocube.api.core.message.Message;
import net.astrocube.api.core.model.Model;

import java.util.Collection;
import java.util.Set;

public interface QueryResult<Complete extends Model> extends Message {

    /**
     * Complete collection of data that will return as part of pagination
     * @return found models
     */
    @JsonProperty("data")
    Set<Complete> getFoundModels();

}