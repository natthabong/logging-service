package gec.scf.logging.batch.client;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import gec.scf.logging.batch.client.payload.BatchTrackingPayload;
import gec.scf.logging.client.LoggingClient;

public class BatchTrackerBuilder {

	private String processNo;

	private String referenceId;

	private LoggingClient client;

	public BatchTrackerBuilder(LoggingClient client) {
		this.client = client;
	}

	public BatchTrackerBuilder referenceId(String referenceId) {
		this.referenceId = referenceId;
		return this;
	}

	public BatchTrackerBuilder processNo(String processNo) {
		this.processNo = processNo;
		return this;
	}

	public BatchTracker build() {

		return new BatchTracker() {

			String action;

			boolean incomplete;

			Map<String, String> parameters = new HashMap<>();

			@Override
			public void track() {
				BatchTrackingPayload payload = new BatchTrackingPayload();
				payload.setReferenceId(referenceId);
				payload.setAction(action);
				payload.setProcessNo(Optional.ofNullable(processNo)
						.orElse(UUID.randomUUID().toString()));
				payload.setParameters(parameters);
				payload.setCompleted(!incomplete);
				incomplete = false;
				client.writeBatchTracking(payload);
			}

			@Override
			public BatchTracker action(String action) {
				this.action = action;
				return this;
			}

			@Override
			public BatchTracker param(String name, String value) {
				parameters.put(name, value);
				return this;
			}

			@Override
			public BatchTracker incomplete() {
				this.incomplete = true;
				return this;
			}
		};
	}

}
