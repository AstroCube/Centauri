package net.astrocube.api.core.service.paginate;

import com.fasterxml.jackson.databind.node.ObjectNode;
import net.astrocube.api.core.model.Model;
import net.astrocube.api.core.service.ModelRequest;

public interface PaginateRequest<Complete extends Model> extends ModelRequest<Complete> {

    /**
     * Pagination query to append at the query
     * @return string with pagination query
     */
    String getPaginateQuery();

    /**
     * Query to be used as filter of the backend
     * @return objectNode portraying query
     */
    ObjectNode getBsonQuery();

}