package gec.scf.logging.batch.domain;

import java.time.LocalDateTime;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.MapKeyColumn;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

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

	@ElementCollection(fetch = FetchType.EAGER)
	@JoinTable(name = "tbl_batch_tracking_parameters", joinColumns = @JoinColumn(name = "id"))
	@MapKeyColumn(name = "param_key")
	@Column(name = "param_value")
	@Type(type = "java.lang.String")
	private Map<String, Object> parameters;

	@Column(name = "is_completed")
	private boolean completed;

	private LocalDateTime actionTime;

	private String node;

	@Column(name = "ip_address")
	private String ipAddress;

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

	public Map<String, Object> getParameters() {
		return parameters;
	}

	public void setParameters(Map<String, Object> parameters) {
		this.parameters = parameters;
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

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

}
