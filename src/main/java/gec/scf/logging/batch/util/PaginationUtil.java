package gec.scf.logging.batch.util;

import java.net.URISyntaxException;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;

public class PaginationUtil {

	public static final int DEFAULT_OFFSET = 1;

	public static final int MIN_OFFSET = 1;

	public static final int DEFAULT_LIMIT = 20;

	public static HttpHeaders generatePaginationHttpHeaders(Page<?> page, Integer offset,
			Integer limit) throws URISyntaxException {

		if (offset == null || offset < MIN_OFFSET) {
			offset = DEFAULT_OFFSET;
		}
		if (limit == null) {
			limit = Integer.MAX_VALUE;
		}
		HttpHeaders headers = new HttpHeaders();
		headers.add("X-Page-Size", String.valueOf(page.getSize()));
		headers.add("X-Total-Count", String.valueOf(page.getTotalElements()));
		headers.add("X-Total-Page", String.valueOf(page.getTotalPages()));

		return headers;
	}
}
