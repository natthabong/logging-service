package gec.scf.logging.batch.util;

import java.time.LocalDateTime;
import java.util.Optional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

public class SpecificationUtils {

	private static final char ESCAPED_WILDCARD_CHAR = '\\';

	public static <V> Specification<V> like(String fieldName, String value) {

		return (Root<V> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {

			return Optional.ofNullable(value)
					.map(SpecificationUtils::escapedWildcardValue)
					.map(val -> cb.like(root.<String>get(fieldName), "%" + val + "%",
							ESCAPED_WILDCARD_CHAR))
					.orElse(exclude());

		};

	}

	public static <V, E> Specification<V> eq(final String fieldName, final E value) {

		return (Root<V> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {

			return Optional.ofNullable(value)
					.map(val -> cb.equal(root.<E>get(fieldName), val)).orElse(exclude());

		};
	}

	public static <V, E> Specification<V> notEqual(final String fieldName,
			final E value) {

		return (Root<V> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {

			return Optional.ofNullable(value)
					.map(val -> cb.notEqual(root.<E>get(fieldName), val))
					.orElse(exclude());

		};
	}

	public static <V> Specification<V> timeBetween(String fieldName, LocalDateTime from,
			LocalDateTime to) {

		return (Root<V> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {

			Predicate predicateFrom = null;
			if (from != null) {
				LocalDateTime fromTime = from.withSecond(0).withNano(0);
				predicateFrom = cb.greaterThanOrEqualTo(
						root.<LocalDateTime>get(fieldName), cb.literal(fromTime));
			}

			Predicate predicateTo = null;
			if (to != null) {
				LocalDateTime toTime = to.plusMinutes(1).withSecond(0).withNano(0);
				predicateTo = cb.lessThan(root.<LocalDateTime>get(fieldName),
						cb.literal(toTime));
			}
			if (from != null && to != null) {
				return cb.and(predicateFrom, predicateTo);
			}
			else if (from != null) {
				return predicateFrom;
			}
			else {
				return predicateTo;
			}
		};

	}

	private static String escapedWildcardValue(String value) {
		// Escape -> [] _ % ^
		value = value.replaceAll("[\\[]", "\\\\[");
		value = value.replaceAll("[\\]]", "\\\\]");
		value = value.replaceAll("[_]", "\\\\_");
		value = value.replaceAll("[%]", "\\\\%");
		value = value.replaceAll("[\\^]", "\\\\^");
		return value;
	}

	private static Predicate exclude() {
		return null;
	}

}
