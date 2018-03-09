package gec.scf.logging.client;

import gec.scf.logging.batch.client.payload.BatchTrackingPayload;

public interface LoggingClient {

	public void writeBatchTracking(BatchTrackingPayload payload);

}
