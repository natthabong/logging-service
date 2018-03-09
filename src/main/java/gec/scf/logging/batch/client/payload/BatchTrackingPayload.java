package gec.scf.logging.batch.client.payload;

import java.io.Serializable;
import java.util.Map;

public class BatchTrackingPayload implements Serializable {

	private static final long serialVersionUID = 1L;

	private String referenceId;

	private String processNo;

	private String action;

	boolean completed;

	private Map<String, String> parameters;

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

	public void setParameters(Map<String, String> parameters) {
		this.parameters = parameters;

	}

	public Map<String, String> getParameters() {
		return parameters;
	}

	public void setCompleted(boolean completed) {
		this.completed = completed;

	}

	public boolean isCompleted() {
		return completed;
	}

}
