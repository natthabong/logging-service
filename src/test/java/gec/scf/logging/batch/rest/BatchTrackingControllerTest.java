package gec.scf.logging.batch.rest;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.reactive.server.WebTestClient.ResponseSpec;

import gec.scf.logging.AppConstants;
import gec.scf.logging.batch.client.payload.BatchTrackingPayload;
import gec.scf.logging.batch.criteria.BatchTrackingCriteria;
import gec.scf.logging.batch.domain.BatchTracking;
import gec.scf.logging.batch.service.BatchTrackingService;
import reactor.core.publisher.Mono;

@RunWith(SpringRunner.class)
@WebFluxTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BatchTrackingControllerTest {

	@Autowired
	private WebTestClient webClient;

	@MockBean
	private BatchTrackingService batchTrackingService;

	public static final <V> Page<V> buildPage(List<V> items) {
		Page<V> page = new PageImpl<V>(items);
		return page;
	}

	// POST Test-case
	@Test
	public void new_batch_log() throws Exception {
		// Arrange
		BatchTrackingPayload batchLog = new BatchTrackingPayload();
		batchLog.setReferenceId("00248");
		batchLog.setProcessNo("THIS_RANDOM_NUMBER");

		ArgumentCaptor<BatchTracking> captor = ArgumentCaptor.forClass(BatchTracking.class);
		LocalDateTime requestTime = LocalDateTime.of(2018, 5, 22, 23, 50);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(AppConstants.RFC_3339_DATE_FORMAT);

		given(batchTrackingService.createBatchTracking(any(BatchTracking.class)))
				.willReturn(Mono.just(BatchTracking.newInstanceFrom(batchLog)));

		// Actual
		ResponseSpec action = webClient.post().uri("/v1/batches").contentType(MediaType.APPLICATION_JSON_UTF8)
				.header("X-Action-Time", formatter.format(requestTime)).header("X-Node-ID", "Scheduler")
				.body(Mono.just(batchLog), BatchTrackingPayload.class).exchange();

		// Assert
		action.expectStatus().isCreated();

		verify(batchTrackingService, times(1)).createBatchTracking(captor.capture());

		BatchTracking actualBatchTracking = captor.getValue();
		assertThat(actualBatchTracking.getActionTime(), is(notNullValue()));
		assertThat(actualBatchTracking.getNode(), is("Scheduler"));
	}

	// GET Test-case
	@Test
	public void when_get_batch_trackings_should_return_data() throws Exception {
		// Arrange
		BatchTracking batchTracking = new BatchTracking();
		batchTracking.setId("UID1");
		batchTracking.setReferenceId("REF01");
		batchTracking.setProcessNo("PID01");
		batchTracking.setAction("START_BATCH_JOB_IMPORT_FILE");
		LocalDateTime requestTime = LocalDateTime.of(2018, 5, 22, 23, 50);
		batchTracking.setActionTime(requestTime);
		batchTracking.setCompleted(true);
		batchTracking.setNode("Batch");
		batchTracking.setIpAddress("127.0.0.1");

		Page<BatchTracking> batchTrackingPage = buildPage(Arrays.asList(batchTracking));
		ArgumentCaptor<BatchTrackingCriteria> captor = ArgumentCaptor.forClass(BatchTrackingCriteria.class);

		given(this.batchTrackingService.getBatchTrackings(any(BatchTrackingCriteria.class)))
				.willReturn(batchTrackingPage);

		// Actual
		ResponseSpec action = webClient.get().uri(uriBuilder -> 
			 uriBuilder.path("/v1/batches").queryParam("batchTrackingId", "UID1").build()
		).exchange();
		// Assert
		action.expectStatus().isOk();

		verify(batchTrackingService, times(1)).getBatchTrackings(captor.capture());

		BatchTrackingCriteria actualBatchTracking = captor.getValue();
		assertThat(actualBatchTracking.getId(), is("UID1"));
	}

}
