package gec.scf.logging.batch.service;

import static gec.scf.logging.batch.util.SpecificationUtils.between;
import static gec.scf.logging.batch.util.SpecificationUtils.eq;
import static gec.scf.logging.batch.util.SpecificationUtils.like;
import static org.springframework.data.jpa.domain.Specification.where;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import gec.scf.logging.batch.criteria.BatchTrackingCriteria;
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

	@Override
	public Page<BatchTracking> getBatchTrackings(final BatchTrackingCriteria batchTrackingCriteria) {

		Specification<BatchTracking> specifications = 
			  where(like(BatchTracking.class, "id", batchTrackingCriteria.getId()))
			 .and(eq(BatchTracking.class, "processNo", batchTrackingCriteria.getProcessNo()))
			 .and(between(BatchTracking.class, "actionTime", batchTrackingCriteria.getLogDateFrom(), batchTrackingCriteria.getLogDateTo()));
		
		Page<BatchTracking> remittanceAdvices = batchTrackingRepository.findAll(specifications,batchTrackingCriteria.getPageable());
		return remittanceAdvices;
	}

}
