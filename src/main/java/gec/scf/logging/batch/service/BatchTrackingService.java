package gec.scf.logging.batch.service;

import gec.scf.logging.batch.domain.BatchTracking;
import reactor.core.publisher.Mono;

public interface BatchTrackingService {

	Mono<BatchTracking> createBatchTracking(BatchTracking batchTracking);

}
