package gec.scf.logging.batch.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import gec.scf.logging.batch.client.payload.BatchTrackingItemPayload;
import gec.scf.logging.batch.client.payload.BatchTrackingPayload;
import gec.scf.logging.client.LoggingClient;

public class BatchTrackerBuilder {

	private String processNo;

	private String referenceId;

	private LoggingClient client;

	public static BatchTrackerBuilder builder(LoggingClient client) {
		return new BatchTrackerBuilder(client);
	}

	private BatchTrackerBuilder(LoggingClient client) {
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

			Map<String, Object> parameters = new HashMap<>();

			private List<BatchTrackingItemPayload> items = new ArrayList<BatchTrackingItemPayload>();

			@Override
			public void track(String completedAction, String incompletedAction) {
				if (items.size() > 0) {
					int totalIncompleted = items.stream()
							.filter(item -> !item.isCompleted())
							.collect(Collectors.toList()).size();
					incomplete = totalIncompleted > 0;
					action = incomplete ? incompletedAction : completedAction;
				}
				this.track();
			}

			@Override
			public void track() {
				BatchTrackingPayload payload = new BatchTrackingPayload();
				payload.setReferenceId(referenceId);
				payload.setAction(action);
				payload.setProcessNo(Optional.ofNullable(processNo)
						.orElse(UUID.randomUUID().toString()));
				payload.setParameters(parameters);
				payload.setCompleted(!incomplete);
				payload.setItems(items);
				payload.setHasDetail(items.size() > 0);
				client.writeBatchTracking(payload);
				action = null;
				parameters = new HashMap<>();
				incomplete = false;
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

			@Override
			public BatchTracker item(BatchTrackingItemPayload item) {
				this.items.add(item);
				return this;
			}

		};
	}

}
