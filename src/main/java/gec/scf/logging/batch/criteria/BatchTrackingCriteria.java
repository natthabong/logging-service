package gec.scf.logging.batch.criteria;

import java.time.ZonedDateTime;
import java.util.Optional;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

public class BatchTrackingCriteria {

	private String referenceId;

	private String processNo;

	private ZonedDateTime logDateFrom;

	private ZonedDateTime logDateTo;

	private int page;

	private int size;

	private Sort sorting;

	public String getProcessNo() {
		return processNo;
	}

	public void setProcessNo(String processNo) {
		this.processNo = processNo;
	}

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

	public ZonedDateTime getLogDateFrom() {
		return logDateFrom;
	}

	public void setLogDateFrom(ZonedDateTime logDateFrom) {
		this.logDateFrom = logDateFrom;
	}

	public ZonedDateTime getLogDateTo() {
		return logDateTo;
	}

	public void setLogDateTo(ZonedDateTime logDateTo) {
		this.logDateTo = logDateTo;
	}

	public Pageable getPageable() {
		return PageRequest.of(page, size, Optional.ofNullable(sorting)
				.orElse(Sort.by(Direction.DESC, "actionTime")));
	}

	public void setReferenceId(String referenceId) {
		this.referenceId = referenceId;
	}

	public String getReferenceId() {
		return referenceId;
	}

}
