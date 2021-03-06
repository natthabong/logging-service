package gec.scf.logging.batch.service;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.time.LocalDateTime;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import gec.scf.logging.batch.criteria.BatchTrackingCriteria;
import gec.scf.logging.batch.criteria.BatchTrackingItemCriteria;
import gec.scf.logging.batch.domain.BatchTracking;
import gec.scf.logging.batch.domain.BatchTrackingItemRepository;
import gec.scf.logging.batch.domain.BatchTrackingRepository;

@RunWith(SpringRunner.class)
public class BatchTrackingServiceTest {

	@Autowired
	BatchTrackingService batchTrackingService;

	@MockBean
	BatchTrackingRepository batchTrackingRepository;
	
	@MockBean
	BatchTrackingItemRepository batchTrackingItemRepository;

	@Before
	public void setup() {
		given(batchTrackingRepository.save(Mockito.any(BatchTracking.class)))
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
		given(batchTracking.getReferenceId()).willReturn("J42");

		ArgumentCaptor<BatchTracking> captor = ArgumentCaptor
				.forClass(BatchTracking.class);

		// Actual
		batchTrackingService.createBatchTracking(batchTracking);

		// Assert
		verify(batchTrackingRepository, times(1)).save(captor.capture());
		BatchTracking actualBatchTracking = captor.getValue();

		assertThat(actualBatchTracking.getReferenceId(), is("J42"));

	}

	@Test
	public void should_get_batch_trackings() {
		// Arrange
		ArgumentCaptor<Pageable> captor = ArgumentCaptor.forClass(Pageable.class);

		BatchTrackingCriteria batchTrackingCriteria = mock(BatchTrackingCriteria.class);
		given(batchTrackingCriteria.getReferenceId()).willReturn("J01");
		given(batchTrackingCriteria.getProcessNo()).willReturn("P01");
		given(batchTrackingCriteria.getLogDateFrom()).willReturn(LocalDateTime.of(2018, 10, 10, 12, 15));
		given(batchTrackingCriteria.getLogDateTo()).willReturn(LocalDateTime.of(2018, 10, 10, 15, 30));

		Pageable pageRequest = PageRequest.of(0, 20);
		given(batchTrackingCriteria.getPageable()).willReturn(pageRequest);

		// Actual
		batchTrackingService.getBatchTrackings(batchTrackingCriteria);

		// Assert
		verify(batchTrackingRepository, times(1)).findAll(any(), captor.capture());

		Pageable actualPage = captor.getValue();
		assertThat(actualPage.getPageSize(), is(20));
		assertThat(actualPage.getPageNumber(), is(0));
	}
	
	@Test
	public void should_get_batch_trackings_items() {
		// Arrange
		ArgumentCaptor<Pageable> captor = ArgumentCaptor.forClass(Pageable.class);

		BatchTrackingItemCriteria batchTrackingItemCriteria = mock(BatchTrackingItemCriteria.class);
		given(batchTrackingItemCriteria.getBatchTrackingId()).willReturn("J01");
		given(batchTrackingItemCriteria.isCompleted()).willReturn(true);

		Pageable pageRequest = PageRequest.of(0, 20);
		given(batchTrackingItemCriteria.getPageable()).willReturn(pageRequest);

		// Actual
		batchTrackingService.getBatchTrackingItems(batchTrackingItemCriteria);

		// Assert
		verify(batchTrackingItemRepository, times(1)).findAll(any(), captor.capture());

		Pageable actualPage = captor.getValue();
		assertThat(actualPage.getPageSize(), is(20));
		assertThat(actualPage.getPageNumber(), is(0));
	}

	@TestConfiguration
	static class BatchTrackingServiceConfiguration {

		@Bean
		BatchTrackingService batchTrackingService(
				@Autowired BatchTrackingRepository batchTrackingRepository,
				@Autowired BatchTrackingItemRepository batchTrackingItemRepository) {
			return new BatchTrackingServiceImpl(batchTrackingRepository,batchTrackingItemRepository);

		}
	}
}
