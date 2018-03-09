package gec.scf.logging.batch.client;

public interface BatchTracker {

	BatchTracker action(String action);

	void track();

	BatchTracker param(String name, String value);

}
