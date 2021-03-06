package gec.scf.logging.batch.client.payload;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class BatchTrackingPayload implements Serializable {

	private static final long serialVersionUID = 1L;

	private String referenceId;

	private String processNo;

	private String action;
	
	private boolean hasDetail;

	boolean completed;

	private Map<String, Object> parameters;

	private List<? extends BatchTrackingItemPayload> items;

	public String getReferenceId() {
		return referenceId;
	}

	public void setReferenceId(String referenceId) {
		this.referenceId = referenceId;
	}

	public void setProcessNo(String processNo) {
		this.processNo = processNo;

	}

	public String getProcessNo() {
		return processNo;
	}

	public void setAction(String action) {
		this.action = action;

	}

	public String getAction() {
		return action;
	}

	public Map<String, Object> getParameters() {
		return parameters;
	}

	public void setParameters(Map<String, Object> parameters) {
		this.parameters = parameters;
	}

	public void setCompleted(boolean completed) {
		this.completed = completed;

	}

	public boolean isCompleted() {
		return completed;
	}

	public List<? extends BatchTrackingItemPayload> getItems() {
		return items;
	}

	public void setItems(List<? extends BatchTrackingItemPayload> items) {
		this.items = items;
	}

	public boolean isHasDetail() {
		return hasDetail;
	}

	public void setHasDetail(boolean hasDetail) {
		this.hasDetail = hasDetail;
	}

}
