package gec.scf.logging.batch.client;

import gec.scf.logging.batch.client.payload.BatchTrackingItemPayload;

public interface BatchTracker {

	BatchTracker action(String action);

	BatchTracker incomplete();

	BatchTracker param(String name, String value);

	void track();

	void track(String completedAction, String incompletedAction);

	BatchTracker item(BatchTrackingItemPayload item);

}
