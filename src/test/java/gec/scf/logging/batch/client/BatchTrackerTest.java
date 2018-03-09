package gec.scf.logging.batch.client;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.hamcrest.CoreMatchers.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import gec.scf.logging.batch.client.payload.BatchTrackingPayload;
import gec.scf.logging.client.LoggingClient;

@RunWith(SpringRunner.class)
public class BatchTrackerTest {

	@Autowired
	BatchTrackerBuilder batchTrackingBuilder;

	@MockBean
	LoggingClient loggingClient;

	@Test
	public void write_action_only() {
		// Arrange
		ArgumentCaptor<BatchTrackingPayload> captor = ArgumentCaptor
				.forClass(BatchTrackingPayload.class);

		BatchTracker batchTracker = batchTrackingBuilder.referenceId("J01").build();

		// Actual
		batchTracker.action("START_BATCH_JOB").track();

		// Assert
		verify(loggingClient, times(1)).writeBatchTracking(captor.capture());

		BatchTrackingPayload actualBatchTracking = captor.getValue();
		assertEquals("START_BATCH_JOB", actualBatchTracking.getAction());
		assertEquals("J01", actualBatchTracking.getReferenceId());
		assertThat(actualBatchTracking.getProcessNo(), is(notNullValue()));
	}

	@Test
	public void write_action_and_use_existing_process_no_only() {
		// Arrange
		ArgumentCaptor<BatchTrackingPayload> captor = ArgumentCaptor
				.forClass(BatchTrackingPayload.class);

		BatchTracker batchTracker = batchTrackingBuilder.referenceId("J01")
				.processNo("00055878787").build();

		// Actual
		batchTracker.action("START_BATCH_JOB").track();

		// Assert
		verify(loggingClient, times(1)).writeBatchTracking(captor.capture());
		BatchTrackingPayload actualBatchTracking = captor.getValue();
		assertEquals("START_BATCH_JOB", actualBatchTracking.getAction());
		assertEquals("00055878787", actualBatchTracking.getProcessNo());
	}

	@Test
	public void write_action_with_parameters() {
		// Arrange
		ArgumentCaptor<BatchTrackingPayload> captor = ArgumentCaptor
				.forClass(BatchTrackingPayload.class);

		BatchTracker batchTracker = batchTrackingBuilder.referenceId("J01").build();

		// Actual
		batchTracker.action("DOWNLOAD_FILE_COMPLETED").param("fileName", "bigc.txt")
				.track();

		// Assert
		verify(loggingClient, times(1)).writeBatchTracking(captor.capture());
		BatchTrackingPayload actualBatchTracking = captor.getValue();
		assertEquals("bigc.txt", actualBatchTracking.getParameters().get("fileName"));
	}

	@Test
	public void write_action_when_incomplete() {
		// Arrange
		ArgumentCaptor<BatchTrackingPayload> captor = ArgumentCaptor
				.forClass(BatchTrackingPayload.class);

		BatchTracker batchTracker = batchTrackingBuilder.referenceId("J01").build();

		// Actual
		batchTracker.action("DOWNLOAD_FILE_INCOMPLETED").incomplete().track();

		// Assert
		verify(loggingClient, times(1)).writeBatchTracking(captor.capture());
		BatchTrackingPayload actualBatchTracking = captor.getValue();
		assertThat(actualBatchTracking.isCompleted(), is(false));
	}

	@TestConfiguration
	static class BatchTrackingBuilderTestConfiguration {

		@Bean
		BatchTrackerBuilder batchTrackingBuilder(@Autowired LoggingClient loggingClient) {
			return new BatchTrackerBuilder(loggingClient);

		}
	}

}
