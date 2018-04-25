package gec.scf.logging.batch.domain;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonView;

import gec.scf.logging.batch.View;
import gec.scf.logging.batch.client.payload.BatchTrackingItemPayload;

@Entity
@Table(name = "tbl_batch_tracking_items")
public class BatchTrackingItem extends BatchTrackingItemPayload {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4342060589453288816L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonView({ View.Full.class, View.Partial.class })
	private Long batchTrackingItemId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "batch_tracking_id", nullable = false)
	private BatchTracking batchTracking;

	@JsonView({ View.Full.class, View.Partial.class })
	private String action;

	@JsonView({ View.Full.class, View.Partial.class })
	private String referenceNo;

	@JsonView({ View.Full.class, View.Partial.class })
	private String transactionNo;

	@JsonView({ View.Full.class, View.Partial.class })
	@Column(name = "is_completed")
	private boolean completed;

	@JsonView({ View.Full.class, View.Partial.class })
	private LocalDateTime actionTime;

	public Long getBatchTrackingItemId() {
		return batchTrackingItemId;
	}

	public void setBatchTrackingItemId(Long batchTrackingItemId) {
		this.batchTrackingItemId = batchTrackingItemId;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
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

	public BatchTracking getBatchTracking() {
		return batchTracking;
	}

	public void setBatchTracking(BatchTracking batchTracking) {
		this.batchTracking = batchTracking;
	}

}
