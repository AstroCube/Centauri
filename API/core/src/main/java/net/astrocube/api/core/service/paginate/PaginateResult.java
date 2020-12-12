package net.astrocube.api.core.service.paginate;

import net.astrocube.api.core.message.Message;
import net.astrocube.api.core.model.Document;
import net.astrocube.api.core.model.Model;

import java.util.Optional;
import java.util.Set;

public interface PaginateResult<Complete extends Model> extends Message {

    Set<Complete> getData();

    Pagination getPagination();

    interface Pagination extends Document {

        int perPage();

        boolean hasPrevPage();

        boolean hasNextPage();

        Optional<Integer> prevPage();

        Optional<Integer> nextPage();

        Optional<Integer> page();

        Optional<Integer> totalPages();

    }

}