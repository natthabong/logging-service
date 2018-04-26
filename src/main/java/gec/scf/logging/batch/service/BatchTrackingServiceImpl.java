package gec.scf.logging.batch.service;

import static gec.scf.logging.batch.util.SpecificationUtils.eq;
import static gec.scf.logging.batch.util.SpecificationUtils.timeBetween;
import static org.springframework.data.jpa.domain.Specification.where;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import gec.scf.logging.batch.criteria.BatchTrackingCriteria;
import gec.scf.logging.batch.domain.BatchTracking;
import gec.scf.logging.batch.domain.BatchTrackingItem;
import gec.scf.logging.batch.domain.BatchTrackingItemRepository;
import gec.scf.logging.batch.domain.BatchTrackingRepository;
import gec.scf.logging.batch.util.SpecificationUtils;
import reactor.core.publisher.Mono;

@Service
public class BatchTrackingServiceImpl implements BatchTrackingService {

	private BatchTrackingRepository batchTrackingRepository;
	
	private BatchTrackingItemRepository batchTrackingItemRepository;

	public BatchTrackingServiceImpl(
			@Autowired BatchTrackingRepository batchTrackingRepository,
			@Autowired BatchTrackingItemRepository batchTrackingItemRepository) {
		this.batchTrackingRepository = batchTrackingRepository;
		this.batchTrackingItemRepository = batchTrackingItemRepository;
	}

	@Transactional(readOnly = false)
	@Override
	public Mono<BatchTracking> createBatchTracking(BatchTracking batchTracking) {
		return Mono.just(batchTrackingRepository.save(batchTracking));
	}

	@Transactional(readOnly = true)
	@Override
	public Page<BatchTracking> getBatchTrackings(
			final BatchTrackingCriteria batchTrackingCriteria) {

		// @formatter:off
		Specification<BatchTracking> specifications = where(SpecificationUtils.
		     <BatchTracking>like("referenceId", batchTrackingCriteria.getReferenceId()))
						 .and(eq("processNo", batchTrackingCriteria.getProcessNo()))
						.and(timeBetween("actionTime", batchTrackingCriteria.getLogDateFrom(),
												   batchTrackingCriteria.getLogDateTo()));
		// @formatter:on
		Page<BatchTracking> batchTrackings = batchTrackingRepository
				.findAll(specifications, batchTrackingCriteria.getPageable());
		return batchTrackings;
	}

	@Transactional(readOnly = true)
	@Override
	public List<BatchTrackingItem> getBatchTrackingItems(String batchTrackingId, boolean isCompleted) {

		List<BatchTrackingItem> batchTrackingItems = batchTrackingItemRepository
				.findByBatchTrackingIdAndCompleted(batchTrackingId, isCompleted);

		return batchTrackingItems;
	}

}
