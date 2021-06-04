package net.astrocube.api.core.service.paginate;

import net.astrocube.api.core.message.Message;
import net.astrocube.api.core.model.Document;
import net.astrocube.api.core.model.Model;

import java.beans.ConstructorProperties;
import java.util.OptionalInt;
import java.util.Set;

/**
 * Represents a model pagination result
 * @param <Complete> The model type
 */
public class PaginateResult<Complete extends Model>
		implements Message {

	private final Set<Complete> data;
	private final Pagination pagination;

	@ConstructorProperties({"data", "pagination"})
	public PaginateResult(Set<Complete> data, Pagination pagination) {
		this.data = data;
		this.pagination = pagination;
	}

	public Set<Complete> getData() {
		return data;
	}

	public Pagination getPagination() {
		return pagination;
	}

	public static class Pagination implements Document {

		private final int perPage;
		private final boolean hasPrevPage;
		private final boolean hasNextPage;

		private final Integer prevPage;
		private final Integer nextPage;
		private final Integer page;
		private final Integer totalPages;

		@ConstructorProperties({
				"perPage", "hasPrevPage", "hasNextPage",
				"prevPage", "nextPage", "page", "totalPages"
		})
		public Pagination(
				int perPage,
				boolean hasPrevPage,
				boolean hasNextPage,
				Integer prevPage,
				Integer nextPage,
				Integer page,
				Integer totalPages
		) {
			this.perPage = perPage;
			this.hasPrevPage = hasPrevPage;
			this.hasNextPage = hasNextPage;
			this.prevPage = prevPage;
			this.nextPage = nextPage;
			this.page = page;
			this.totalPages = totalPages;
		}

		public int getPerPage() {
			return perPage;
		}

		public boolean hasPrevPage() {
			return hasPrevPage;
		}

		public boolean hasNextPage() {
			return hasNextPage;
		}

		public int getPrevPage() {
			return hasPrevPage ? prevPage : -1;
		}

		public int getNextPage() {
			return hasNextPage ? nextPage : -1;
		}

		public OptionalInt getPage() {
			return page == null ? OptionalInt.empty() : OptionalInt.of(page);
		}

		public OptionalInt getTotalPages() {
			return totalPages == null ? OptionalInt.empty() : OptionalInt.of(totalPages);
		}

	}

}