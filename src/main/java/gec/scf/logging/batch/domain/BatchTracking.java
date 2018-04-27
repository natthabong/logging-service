package gec.scf.logging.batch.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.MapKeyColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonView;

import gec.scf.logging.batch.View;
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
	@JsonView({ View.Full.class, View.Partial.class })
	private String id;

	@JsonView({ View.Full.class, View.Partial.class })
	private String referenceId;

	@JsonView({ View.Full.class, View.Partial.class })
	private String processNo;

	@JsonView({ View.Full.class, View.Partial.class })
	private String action;

	@JsonView({ View.Full.class, View.Partial.class })
	@ElementCollection(fetch = FetchType.EAGER)
	@JoinTable(name = "tbl_batch_tracking_parameters", joinColumns = @JoinColumn(name = "id"))
	@MapKeyColumn(name = "param_key")
	@Column(name = "param_value")
	@Type(type = "java.lang.String")
	private Map<String, Object> parameters;

	@JsonView({ View.Full.class, View.Partial.class })
	@Column(name = "is_completed")
	private boolean completed;

	@JsonView({ View.Full.class, View.Partial.class })
	private LocalDateTime actionTime;

	@JsonView({ View.Full.class, View.Partial.class })
	private String node;

	@JsonView({ View.Full.class, View.Partial.class })
	@Column(name = "ip_address")
	private String ipAddress;

	@JsonView({ View.Full.class })
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "batchTracking")
	private List<BatchTrackingItem> items = new ArrayList<>();

	@JsonView({ View.Partial.class })
	@Column(name = "has_detail")
	private boolean hasDetail;

	@Override
	public boolean isHasDetail() {
		return hasDetail;
	}

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

		if (payload.getItems() != null) {
			batchTracking.setHasDetail(payload.getItems().size() > 0);
			batchTracking.getItems().addAll(payload.getItems().stream().map(i -> {
				BatchTrackingItem item = new BatchTrackingItem();
				item.setActionTime(i.getActionTime());
				item.setAction(i.getAction());
				item.setCompleted(i.isCompleted());
				item.setReferenceNo(i.getReferenceNo());
				item.setTransactionNo(i.getTransactionNo());
				item.setBatchTracking(batchTracking);
				return item;
			}).collect(Collectors.toList()));
		}

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

	public List<BatchTrackingItem> getItems() {
		return items;
	}

	public void setHasDetail(boolean hasDetail) {
		this.hasDetail = hasDetail;
	}

}
