package gec.scf.logging.batch.criteria;

import java.util.Date;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class BatchTrackingCriteria {

	private String id;

	private String processNo;

	private Date logDateFrom;

	private Date logDateTo;
	
	private Integer offset;

	private Integer limit;

	private Sort sorting;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getProcessNo() {
		return processNo;
	}

	public void setProcessNo(String processNo) {
		this.processNo = processNo;
	}

	public Date getLogDateFrom() {
		return logDateFrom;
	}

	public void setLogDateFrom(Date logDateFrom) {
		this.logDateFrom = logDateFrom;
	}

	public Date getLogDateTo() {
		return logDateTo;
	}

	public void setLogDateTo(Date logDateTo) {
		this.logDateTo = logDateTo;
	}

	public Integer getOffset() {
		return offset;
	}

	public void setOffset(Integer offset) {
		this.offset = offset;
	}

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	public Sort getSorting() {
		return sorting;
	}

	public void setSorting(Sort sorting) {
		this.sorting = sorting;
	}
	
	public Pageable getPageable() {
		return PageRequest.of(offset, limit, sorting);
	}
	
}
