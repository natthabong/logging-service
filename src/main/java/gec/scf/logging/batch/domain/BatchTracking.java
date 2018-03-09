package gec.scf.logging.batch.domain;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import gec.scf.logging.batch.client.payload.BatchTrackingPayload;

@Entity
@Table(name = "tbl_batch_tracking")
public class BatchTracking extends BatchTrackingPayload {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5324689908284540062L;

	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	private String id;

	private String referenceId;

	private String processNo;

	private String action;

	@Column(name = "is_completed")
	private boolean completed;

	private LocalDateTime actionTime;

	private String node;

	public LocalDateTime getActionTime() {
		return actionTime;
	}

	public void setActionTime(LocalDateTime actionTime) {
		this.actionTime = actionTime;
	}

	public void setNode(String node) {
		this.node = node;

	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNode() {
		return node;
	}

	public static BatchTracking newInstanceFrom(BatchTrackingPayload payload) {
		BatchTracking batchTracking = new BatchTracking();
		batchTracking.setProcessNo(payload.getProcessNo());
		batchTracking.setReferenceId(payload.getReferenceId());
		batchTracking.setAction(payload.getAction());
		batchTracking.setCompleted(payload.isCompleted());
		batchTracking.setParameters(payload.getParameters());

		return batchTracking;

	}

	public String getReferenceId() {
		return referenceId;
	}

	public void setReferenceId(String referenceId) {
		this.referenceId = referenceId;
	}

	public String getProcessNo() {
		return processNo;
	}

	public void setProcessNo(String processNo) {
		this.processNo = processNo;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public boolean isCompleted() {
		return completed;
	}

	public void setCompleted(boolean completed) {
		this.completed = completed;
	}

}
