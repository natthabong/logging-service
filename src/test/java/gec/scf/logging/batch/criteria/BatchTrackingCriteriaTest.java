package gec.scf.logging.batch.criteria;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.springframework.data.domain.Pageable;

public class BatchTrackingCriteriaTest {

	@Test
	public void get_pagable_from_criteria() {

		// Arrange
		BatchTrackingCriteria criteria = new BatchTrackingCriteria();
		criteria.setSize(20);
		criteria.setPage(0);

		// Actual
		Pageable actualPage = criteria.getPageable();

		// Assert
		assertThat(actualPage.getPageSize(), is(20));
		assertThat(actualPage.getPageNumber(), is(0));
	}

}
