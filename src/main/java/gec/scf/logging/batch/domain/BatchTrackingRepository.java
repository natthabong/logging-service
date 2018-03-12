package gec.scf.logging.batch.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.CrudRepository;

public interface BatchTrackingRepository extends CrudRepository<BatchTracking, String> {
	
	Page<BatchTracking> findAll(Specification<BatchTracking> specifications, Pageable pageable);

}
