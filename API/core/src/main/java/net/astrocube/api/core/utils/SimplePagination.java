package net.astrocube.api.core.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class SimplePagination<T> implements Pagination<T> {

	private final int pageSize;
	private final List<T> objects;

	public SimplePagination(int pageSize, List<T> objects) {
		this.pageSize = pageSize;
		this.objects = objects;
	}

	public SimplePagination(int pageSize, Set<T> objects) {
		this.pageSize = pageSize;
		this.objects = new ArrayList<>(objects);
	}

	@Override
	public int getPageSize() {
		return this.pageSize;
	}

	@Override
	public List<T> getObjects() {
		return this.objects;
	}

	public boolean pageExists(int page) {
		return !(page < 0) && page < totalPages();
	}

	public int totalPages() {
		return (int) Math.ceil((double) this.objects.size() / this.pageSize);
	}

	public List<T> getPage(int page) {
		List<T> pageResult = new ArrayList<>();

		int min = (page * this.pageSize) - pageSize;
		int max = page * this.pageSize - 1;

		if (max > this.objects.size()) max = this.objects.size();

		for (int i = min; max > i; i++) {
			pageResult.add(this.objects.get(i));
		}

		return pageResult;
	}
}
