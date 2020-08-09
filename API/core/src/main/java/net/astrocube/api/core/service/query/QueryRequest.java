package net.astrocube.api.core.service.query;

import com.fasterxml.jackson.databind.node.ObjectNode;
import net.astrocube.api.core.model.Model;
import net.astrocube.api.core.service.ModelRequest;

import javax.annotation.Nullable;

public interface QueryRequest<Complete extends Model> extends ModelRequest<Complete> {

    /**
     * Query that will be used as body for the request
     * @return ObjectNode to be serialized
     */
    @Nullable
    ObjectNode getBsonQuery();

}