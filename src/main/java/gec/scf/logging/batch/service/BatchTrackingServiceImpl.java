package gec.scf.logging.batch.service;

import static gec.scf.logging.batch.util.SpecificationUtils.*;
import static gec.scf.logging.batch.util.SpecificationUtils.eq;
import static org.springframework.data.jpa.domain.Specification.where;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import gec.scf.logging.batch.criteria.BatchTrackingCriteria;
import gec.scf.logging.batch.domain.BatchTracking;
import gec.scf.logging.batch.domain.BatchTrackingRepository;
import gec.scf.logging.batch.util.SpecificationUtils;
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
		Page<BatchTracking> remittanceAdvices = batchTrackingRepository
				.findAll(specifications, batchTrackingCriteria.getPageable());
		return remittanceAdvices;
	}

}
