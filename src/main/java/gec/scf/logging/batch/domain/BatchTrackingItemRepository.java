package gec.scf.logging.batch.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.CrudRepository;

public interface BatchTrackingItemRepository extends CrudRepository<BatchTrackingItem, String> {

	Page<BatchTrackingItem> findAll(Specification<BatchTrackingItem> specifications, Pageable pageable);

}
