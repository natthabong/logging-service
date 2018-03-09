package gec.scf.logging.batch.service;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import gec.scf.logging.batch.domain.BatchTracking;
import gec.scf.logging.batch.domain.BatchTrackingRepository;

@RunWith(SpringRunner.class)
public class BatchTrackingServiceTest {

	@Autowired
	BatchTrackingService batchTrackingService;

	@MockBean
	BatchTrackingRepository batchTrackingRepository;

	@Before
	public void setup() {
		BDDMockito.given(batchTrackingRepository.save(Mockito.any(BatchTracking.class)))
				.willAnswer(invocation -> {
					Object[] args = invocation.getArguments();
					return args[0];
				});

	}

	@Test
	public void should_save_batch_tracking() {
		// Arrange
		BatchTracking batchTracking = mock(BatchTracking.class);

		// Actual
		batchTrackingService.createBatchTracking(batchTracking);

		// Assert
		verify(batchTrackingRepository, times(1)).save(any(BatchTracking.class));

	}

	@Test
	public void should_save_batch_tracking_with_correcly_referenceId() {
		// Arrange
		BatchTracking batchTracking = mock(BatchTracking.class);
		BDDMockito.given(batchTracking.getReferenceId()).willReturn("J42");

		ArgumentCaptor<BatchTracking> captor = ArgumentCaptor
				.forClass(BatchTracking.class);

		// Actual
		batchTrackingService.createBatchTracking(batchTracking);

		// Assert
		verify(batchTrackingRepository, times(1)).save(captor.capture());
		BatchTracking actualBatchTracking = captor.getValue();

		assertThat(actualBatchTracking.getReferenceId(), is("J42"));

	}

	@TestConfiguration
	static class BatchTrackingServiceConfiguration {

		@Bean
		BatchTrackingService batchTrackingService(
				@Autowired BatchTrackingRepository batchTrackingRepository) {
			return new BatchTrackingServiceImpl(batchTrackingRepository);

		}
	}
}
