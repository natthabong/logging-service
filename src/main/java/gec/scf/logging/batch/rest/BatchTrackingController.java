package gec.scf.logging.batch.rest;

import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;

import gec.scf.logging.batch.View;
import gec.scf.logging.batch.client.ServiceTypes;
import gec.scf.logging.batch.client.payload.BatchTrackingPayload;
import gec.scf.logging.batch.criteria.BatchTrackingCriteria;
import gec.scf.logging.batch.criteria.BatchTrackingItemCriteria;
import gec.scf.logging.batch.domain.BatchTracking;
import gec.scf.logging.batch.domain.BatchTrackingItem;
import gec.scf.logging.batch.service.BatchTrackingService;
import gec.scf.logging.batch.util.PaginationUtil;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(ServiceTypes.BATCH_TRACKING)
public class BatchTrackingController {

	@Autowired
	private BatchTrackingService batchTrackingService;

	@JsonView(View.Partial.class)
	@PostMapping
	public Mono<ResponseEntity<BatchTracking>> createBatchTracking(
			@RequestHeader("X-Action-Time") String requestTime,
			@RequestHeader("X-Node-ID") String node,
			@RequestBody BatchTrackingPayload payload, ServerHttpRequest request) {

		BatchTracking batchTracking = BatchTracking.newInstanceFrom(payload);
		batchTracking.setActionTime(
				LocalDateTime.parse(requestTime, DateTimeFormatter.ISO_DATE_TIME));
		batchTracking.setNode(node);
		batchTracking.setIpAddress(ipAddressOf(request));

		return batchTrackingService.createBatchTracking(batchTracking)
				.map(resultBatchTracking -> new ResponseEntity<>(resultBatchTracking,
						HttpStatus.CREATED))
				.defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	@JsonView(View.Partial.class)
	@GetMapping
	public ResponseEntity<?> getBatchTracking(
			@RequestParam(required = false) String referenceId,
			@RequestParam(required = false) String logDateFrom,
			@RequestParam(required = false) String logDateTo,
			@RequestParam(required = false) String processNo,
			@RequestParam(required = false) Integer page,
			@RequestParam(required = false) Integer size) throws URISyntaxException {

		final DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;

		BatchTrackingCriteria batchTrackingCriteria = new BatchTrackingCriteria();
		batchTrackingCriteria.setReferenceId(referenceId);
		batchTrackingCriteria.setLogDateFrom(Optional.ofNullable(logDateFrom)
				.map(date -> LocalDateTime.parse(date, formatter)).orElse(null));

		batchTrackingCriteria.setLogDateTo(Optional.ofNullable(logDateTo)
				.map(date -> LocalDateTime.parse(date, formatter)).orElse(null));
		batchTrackingCriteria.setProcessNo(processNo);
		batchTrackingCriteria.setPage(Optional.ofNullable(page).orElse(0));
		batchTrackingCriteria
				.setSize(Optional.ofNullable(size).orElse(Integer.MAX_VALUE));

		Page<BatchTracking> batchTrackingPages = batchTrackingService
				.getBatchTrackings(batchTrackingCriteria);

		List<BatchTracking> batchTrackings = batchTrackingPages.getContent();
		HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(
				batchTrackingPages, batchTrackingPages.getNumber(),
				batchTrackingPages.getSize());

		return new ResponseEntity<>(batchTrackings, headers, HttpStatus.OK);
	}

	private String ipAddressOf(ServerHttpRequest request) {
		String remoteAddr = "";

		if (request != null) {
			remoteAddr = request.getHeaders().getFirst("X-FORWARDED-FOR");
			if (remoteAddr == null || "".equals(remoteAddr)) {
				remoteAddr = request.getRemoteAddress().getAddress().getHostAddress();
			}
		}

		return remoteAddr;
	}
	
	@JsonView(View.Partial.class)
	@RequestMapping(value = "/{batchTrackingId}/details", method = RequestMethod.GET)
	public ResponseEntity<?> getBatchTrackingItems(@PathVariable String batchTrackingId,
			@RequestParam(required = false) Boolean completed,
			@RequestParam(required = false) Integer page,
			@RequestParam(required = false) Integer size) throws URISyntaxException {

		BatchTrackingItemCriteria batchTrackingItemCriteria = new BatchTrackingItemCriteria();
		batchTrackingItemCriteria.setBatchTrackingId(batchTrackingId);
		batchTrackingItemCriteria.setCompleted(completed);
		batchTrackingItemCriteria.setPage(Optional.ofNullable(page).orElse(0));
		batchTrackingItemCriteria
				.setSize(Optional.ofNullable(size).orElse(Integer.MAX_VALUE));

		Page<BatchTrackingItem> batchTrackingItemPages = batchTrackingService
				.getBatchTrackingItems(batchTrackingItemCriteria);

		List<BatchTrackingItem> batchTrackingItems = batchTrackingItemPages.getContent();
		HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(
				batchTrackingItemPages, batchTrackingItemPages.getNumber(),
				batchTrackingItemPages.getSize());

		return new ResponseEntity<>(batchTrackingItems, headers, HttpStatus.OK);
	}
}
