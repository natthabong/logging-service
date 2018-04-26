package gec.scf.logging.batch.criteria;

import java.util.Optional;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

public class BatchTrackingItemCriteria {

	private String batchTrackingId;

	private boolean isCompleted;

	private int page;

	private int size;

	private Sort sorting;


	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	public Sort getSorting() {
		return sorting;
	}

	public void setSorting(Sort sorting) {
		this.sorting = sorting;
	}


	public Pageable getPageable() {
		return PageRequest.of(page, size, Optional.ofNullable(sorting)
				.orElse(Sort.by(Direction.DESC, "actionTime")));
	}

	public String getBatchTrackingId() {
		return batchTrackingId;
	}

	public void setBatchTrackingId(String batchTrackingId) {
		this.batchTrackingId = batchTrackingId;
	}

	public boolean isCompleted() {
		return isCompleted;
	}

	public void setCompleted(boolean isCompleted) {
		this.isCompleted = isCompleted;
	}

	public void setSize(int size) {
		this.size = size;
	}

}
