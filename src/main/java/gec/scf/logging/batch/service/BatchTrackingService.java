package gec.scf.logging.batch.service;

import java.util.List;

import org.springframework.data.domain.Page;

import gec.scf.logging.batch.criteria.BatchTrackingCriteria;
import gec.scf.logging.batch.domain.BatchTracking;
import gec.scf.logging.batch.domain.BatchTrackingItem;
import reactor.core.publisher.Mono;

public interface BatchTrackingService {

	Mono<BatchTracking> createBatchTracking(BatchTracking batchTracking);
	
	Page<BatchTracking> getBatchTrackings(BatchTrackingCriteria batchTrackingCriteria);
	
	List<BatchTrackingItem> getBatchTrackingItems(String batchTrackingId, boolean isCompleted);

}
