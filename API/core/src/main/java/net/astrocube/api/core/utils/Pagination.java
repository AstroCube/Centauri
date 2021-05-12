package net.astrocube.api.core.utils;

import java.util.List;

public interface Pagination<T> {

	int getPageSize();

	List<T> getObjects();

	boolean pageExists(int page);

	int totalPages();

	List<T> getPage(int page);
}