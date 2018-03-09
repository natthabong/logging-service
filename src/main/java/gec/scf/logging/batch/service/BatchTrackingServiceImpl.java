package gec.scf.logging.batch.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import gec.scf.logging.batch.domain.BatchTracking;
import gec.scf.logging.batch.domain.BatchTrackingRepository;
import reactor.core.publisher.Mono;

@Service
public class BatchTrackingServiceImpl implements BatchTrackingService {

	private BatchTrackingRepository batchTrackingRepository;

	public BatchTrackingServiceImpl(
			@Autowired BatchTrackingRepository batchTrackingRepository) {
		this.batchTrackingRepository = batchTrackingRepository;
	}

	@Transactional(readOnly = false)
	@Override
	public Mono<BatchTracking> createBatchTracking(BatchTracking batchTracking) {
		return Mono.just(batchTrackingRepository.save(batchTracking));
	}

}
