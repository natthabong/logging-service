package gec.scf.logging.batch.domain;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface BatchTrackingItemRepository extends CrudRepository<BatchTrackingItem, String> {

	List<BatchTrackingItem> findByBatchTrackingIdAndCompleted(String batchTrackingId, boolean isCompleted);

}
