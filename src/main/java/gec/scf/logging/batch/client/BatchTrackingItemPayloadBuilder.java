package gec.scf.logging.batch.client;

import java.time.ZoneId;
import java.util.Date;

import gec.scf.logging.batch.client.payload.BatchTrackingItemPayload;

public class BatchTrackingItemPayloadBuilder {

	private String action;

	private String referenceNo;

	private String transactionNo;

	private Date actionTime;

	private boolean incomplete;

	public static BatchTrackingItemPayloadBuilder builder() {
		return new BatchTrackingItemPayloadBuilder();
	}

	private BatchTrackingItemPayloadBuilder() {
	}

	public BatchTrackingItemPayloadBuilder referenceNo(String referenceNo) {
		this.referenceNo = referenceNo;
		return this;
	}

	public BatchTrackingItemPayloadBuilder transactionNo(String transactionNo) {
		this.transactionNo = transactionNo;
		return this;
	}

	public BatchTrackingItemPayloadBuilder action(String action) {
		this.action = action;
		return this;
	}

	public BatchTrackingItemPayloadBuilder actionTime(Date actionTime) {
		this.actionTime = actionTime;
		return this;
	}

	public BatchTrackingItemPayloadBuilder incomplete() {
		this.incomplete = true;
		return this;
	}

	public BatchTrackingItemPayload build() {
		BatchTrackingItemPayload itemPayload = new BatchTrackingItemPayload();
		itemPayload.setReferenceNo(referenceNo);
		itemPayload.setAction(action);
		itemPayload.setTransactionNo(transactionNo);
		itemPayload.setActionTime(
				actionTime.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
		itemPayload.setCompleted(!incomplete);
		action = null;
		actionTime = null;
		incomplete = false;
		transactionNo = null;
		referenceNo = null;
		return itemPayload;
	}
}
