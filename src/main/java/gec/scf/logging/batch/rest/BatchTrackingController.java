package gec.scf.logging.batch.rest;

import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import gec.scf.logging.AppConstants;
import gec.scf.logging.batch.client.ServiceTypes;
import gec.scf.logging.batch.client.payload.BatchTrackingPayload;
import gec.scf.logging.batch.criteria.BatchTrackingCriteria;
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
	

	@GetMapping
	public ResponseEntity<?> getBatchTracking(
			@RequestParam(required = true) String batchTrackingId,
			@RequestParam(required = false) @DateTimeFormat(iso = ISO.DATE_TIME) Date logDateFrom,
			@RequestParam(required = false) @DateTimeFormat(iso = ISO.DATE_TIME) Date logDateTo,
			@RequestParam(required = false) String processNo,
			@RequestParam(required = false) Integer offset,
			@RequestParam(required = false) Integer limit,
			@RequestParam(required = false) Sort sorting)
			throws URISyntaxException {

		BatchTrackingCriteria batchTrackingCriteria = new BatchTrackingCriteria();
		batchTrackingCriteria.setId(batchTrackingId);
		batchTrackingCriteria.setLogDateFrom(logDateFrom);
		batchTrackingCriteria.setLogDateTo(logDateTo);
		batchTrackingCriteria.setProcessNo(processNo);
		batchTrackingCriteria.setOffset(offset);
		batchTrackingCriteria.setLimit(limit);
		batchTrackingCriteria.setSorting(sorting);

		Page<BatchTracking> batchTrackingPages = batchTrackingService.getBatchTrackings(batchTrackingCriteria);

		return new ResponseEntity<>(batchTrackingPages, HttpStatus.OK);
	}
}
