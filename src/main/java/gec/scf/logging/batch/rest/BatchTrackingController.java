package gec.scf.logging.batch.rest;

import java.io.Serializable;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import gec.scf.logging.batch.client.ServiceTypes;
import gec.scf.logging.batch.client.payload.BatchTrackingPayload;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(ServiceTypes.BATCH_TRACKING)
public class BatchTrackingController {

	@PostMapping
	public Mono<ResponseEntity<Serializable>> createBatchTracking(
			@RequestBody BatchTrackingPayload batchLog) {

		return Mono.just(new ResponseEntity<>(batchLog, HttpStatus.CREATED));
	}
}
