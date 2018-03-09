package gec.scf.logging.batch.rest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import gec.scf.logging.AppConstants;
import gec.scf.logging.batch.client.ServiceTypes;
import gec.scf.logging.batch.client.payload.BatchTrackingPayload;
import gec.scf.logging.batch.domain.BatchTracking;
import gec.scf.logging.batch.service.BatchTrackingService;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(ServiceTypes.BATCH_TRACKING)
public class BatchTrackingController {

	@Autowired
	private BatchTrackingService batchTrackingService;

	@PostMapping
	public Mono<ResponseEntity<BatchTracking>> createBatchTracking(
			@RequestHeader("X-Action-Time") String requestTime,
			@RequestHeader("X-Node-ID") String node,
			@RequestBody BatchTrackingPayload payload) {

		BatchTracking batchTracking = BatchTracking.newInstanceFrom(payload);
		DateTimeFormatter formatter = DateTimeFormatter
				.ofPattern(AppConstants.RFC_3339_DATE_FORMAT);
		batchTracking.setActionTime(LocalDateTime.parse(requestTime, formatter));
		batchTracking.setNode(node);

		return batchTrackingService.createBatchTracking(batchTracking)
				.map(resultBatchTracking -> new ResponseEntity<>(resultBatchTracking,
						HttpStatus.CREATED))
				.defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}
}
