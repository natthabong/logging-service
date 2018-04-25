package gec.scf.logging.batch.client.payload;

import java.io.Serializable;
import java.time.LocalDateTime;

public class BatchTrackingItemPayload implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8480128904265838778L;

	private String action;

	private String referenceNo;

	private String transactionNo;

	private boolean completed;

	private LocalDateTime actionTime;

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public void actionTime(LocalDateTime actionTime) {
		this.actionTime = actionTime;
	}

	public String getReferenceNo() {
		return referenceNo;
	}

	public void setReferenceNo(String referenceNo) {
		this.referenceNo = referenceNo;
	}

	public String getTransactionNo() {
		return transactionNo;
	}

	public void setTransactionNo(String transactionNo) {
		this.transactionNo = transactionNo;
	}

	public boolean isCompleted() {
		return completed;
	}

	public void setCompleted(boolean completed) {
		this.completed = completed;
	}

	public LocalDateTime getActionTime() {
		return actionTime;
	}

	public void setActionTime(LocalDateTime actionTime) {
		this.actionTime = actionTime;
	}

}
