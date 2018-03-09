package gec.scf.logging.batch.rest;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.reactive.server.WebTestClient.ResponseSpec;

import gec.scf.logging.batch.client.payload.BatchTrackingPayload;
import reactor.core.publisher.Mono;

@RunWith(SpringRunner.class)
@WebFluxTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BatchLogControllerTest {

	@Autowired
	private WebTestClient webClient;

	// POST Test-case
	@Test
	public void new_batch_log() throws Exception {
		// Arrange
		BatchTrackingPayload batchLog = new BatchTrackingPayload();
		batchLog.setReferenceId("00248");
		batchLog.setProcessNo("THIS_RANDOM_NUMBER");

		// Actual
		ResponseSpec action = webClient.post().uri("/v1/batches")
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.body(Mono.just(batchLog), BatchTrackingPayload.class).exchange();

		// Assert
		// @formatter:off
		action.expectStatus().isCreated();
		// @formatter:off
	}

}
