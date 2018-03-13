package gec.scf.logging.batch.util;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Optional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

public class SpecificationUtils {

	private static final char ESCAPED_WILDCARD_CHAR = '\\';

	/**
	 * @Deprecated As of iteration 48, replaced by {@link #like()}
	 */
	@Deprecated
	public static <V> Specification<V> like(Class<V> clazz, String fieldName,
			String value) {

		return like(fieldName, value);

	}

	public static <V> Specification<V> like(String fieldName, String value) {

		return (Root<V> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {

			return Optional.ofNullable(value)
					.map(SpecificationUtils::escapedWildcardValue)
					.map(val -> cb.like(root.<String>get(fieldName), "%" + val + "%",
							ESCAPED_WILDCARD_CHAR))
					.orElse(exclude());

		};

	}

	/**
	 * @Deprecated As of iteration 48, replaced by {@link #eq()}
	 */
	@Deprecated
	public static <V, E> Specification<V> eq(Class<V> clazz, final String fieldName,
			final E value) {

		return eq(fieldName, value);
	}

	public static <V, E> Specification<V> eq(final String fieldName, final E value) {

		return (Root<V> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {

			return Optional.ofNullable(value)
					.map(val -> cb.equal(root.<E>get(fieldName), val)).orElse(exclude());

		};
	}

	/**
	 * @Deprecated As of iteration 48, replaced by {@link #notEqual()}
	 */
	@Deprecated
	public static <V, E> Specification<V> notEqual(Class<V> clazz, final String fieldName,
			final E value) {

		return notEqual(fieldName, value);
	}

	public static <V, E> Specification<V> notEqual(final String fieldName,
			final E value) {

		return (Root<V> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {

			return Optional.ofNullable(value)
					.map(val -> cb.notEqual(root.<E>get(fieldName), val))
					.orElse(exclude());

		};
	}

	public static <V> Specification<V> between(String fieldName, Date from, Date to) {

		return (Root<V> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {

			Predicate predicateFrom = null;
			if (from != null) {
				Calendar cal = Calendar.getInstance(Locale.US);
				cal.setTime(from);
				cal.set(Calendar.HOUR_OF_DAY, 0);
				cal.set(Calendar.MINUTE, 0);
				cal.set(Calendar.SECOND, 0);
				cal.set(Calendar.MILLISECOND, 0);

				predicateFrom = cb.greaterThanOrEqualTo(root.<Date>get(fieldName),
						cb.literal(cal.getTime()));
			}

			Predicate predicateTo = null;
			if (to != null) {
				Calendar cal = Calendar.getInstance(Locale.US);
				cal.setTime(to);
				cal.add(Calendar.DATE, 1);
				cal.set(Calendar.HOUR_OF_DAY, 0);
				cal.set(Calendar.MINUTE, 0);
				cal.set(Calendar.SECOND, 0);
				cal.set(Calendar.MILLISECOND, 0);
				predicateTo = cb.lessThan(root.<Date>get(fieldName),
						cb.literal(cal.getTime()));
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

	/**
	 * @Deprecated As of iteration 48, replaced by {@link #between()}
	 */
	@Deprecated
	public static <V> Specification<V> between(Class<V> clazz, final String fieldName,
			final Date from, final Date to) {
		return between(fieldName, from, to);
	}

	/**
	 * @Deprecated As of iteration 48, replaced by {@link #lessThanOrEqual()}
	 */
	@Deprecated
	public static <V> Specification<V> lessThanOrEqual(Class<V> clazz, String fieldName,
			Date targetDate) {

		return lessThanOrEqual(fieldName, targetDate);
	}

	public static <V> Specification<V> lessThanOrEqual(String fieldName,
			Date targetDate) {

		return (Root<V> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {

			return Optional.ofNullable(targetDate).map(SpecificationUtils::nextDay)
					.map(date -> cb.lessThan(root.<Date>get(fieldName), cb.literal(date)))
					.orElse(exclude());

		};
	}

	/**
	 * @Deprecated As of iteration 48, replaced by {@link #lessThan()}
	 */
	@Deprecated
	public static <V> Specification<V> lessThan(Class<V> clazz, final String fieldName,
			final Date targetDate) {
		return lessThan(fieldName, targetDate);
	}

	public static <V> Specification<V> lessThan(final String fieldName,
			final Date targetDate) {
		return (Root<V> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {

			return Optional.ofNullable(targetDate).map(SpecificationUtils::excludeTime)
					.map(date -> cb.lessThan(root.<Date>get(fieldName), cb.literal(date)))
					.orElse(exclude());

		};
	}

	/**
	 * @Deprecated As of iteration 48, replaced by {@link #isNull()}
	 */
	@Deprecated
	public static <V> Specification<V> isNull(Class<V> clazz, final String fieldName) {

		return isNull(fieldName);
	}

	public static <V> Specification<V> isNull(final String fieldName) {

		return (Root<V> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {

			return cb.isNull(root.get(fieldName));

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

	private static Date nextDay(Date targetDate) {
		Calendar cal = Calendar.getInstance(Locale.US);
		cal.setTime(targetDate);
		cal.add(Calendar.DATE, 1);
		return cal.getTime();
	}

	private static Date excludeTime(Date targetDate) {
		Calendar cal = Calendar.getInstance(Locale.US);
		cal.setTime(targetDate);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

}
