package gec.scf.logging.batch.client;

public interface BatchTracker {

	BatchTracker action(String action);

	BatchTracker incomplete();

	BatchTracker param(String name, String value);

	void track();

}
