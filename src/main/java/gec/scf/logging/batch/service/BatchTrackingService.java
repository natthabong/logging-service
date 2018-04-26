package gec.scf.logging.batch.service;

import org.springframework.data.domain.Page;

import gec.scf.logging.batch.criteria.BatchTrackingCriteria;
import gec.scf.logging.batch.criteria.BatchTrackingItemCriteria;
import gec.scf.logging.batch.domain.BatchTracking;
import gec.scf.logging.batch.domain.BatchTrackingItem;
import reactor.core.publisher.Mono;

public interface BatchTrackingService {

	Mono<BatchTracking> createBatchTracking(BatchTracking batchTracking);
	
	Page<BatchTracking> getBatchTrackings(BatchTrackingCriteria batchTrackingCriteria);
	
	Page<BatchTrackingItem> getBatchTrackingItems(BatchTrackingItemCriteria batchTrackingItemOCriteria);

}
