package net.astrocube.api.core.service.paginate;

import com.fasterxml.jackson.databind.node.ArrayNode;
import net.astrocube.api.core.message.Message;

public interface PaginateBaseResult extends Message {

    ArrayNode getData();

    PaginateResult.Pagination getPagination();

}