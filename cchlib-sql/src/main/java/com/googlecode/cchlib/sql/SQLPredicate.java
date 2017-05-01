package com.googlecode.cchlib.sql;

import java.sql.SQLException;
import java.util.Objects;
import java.util.function.Predicate;

/**
 * Represents a predicate (boolean-valued function) of one argument,
 * difference with {@link Predicate} is that {@link SQLPredicate} is
 * able to support {@link SQLException} during evaluation.
 *
 * @param <T> the type of the input to the predicate
 *
 * @see Predicate
 * @since 4.2
 */
@FunctionalInterface
public interface SQLPredicate<T>
{
    boolean test( T t ) throws SQLException;

    /**
     * Returns a composed predicate that represents a short-circuiting logical AND of this
     * predicate and another. When evaluating the composed predicate, if this predicate is
     * {@code false}, then the {@code other} predicate is not evaluated.
     *
     * <p>
     * Any exceptions thrown during evaluation of either predicate are relayed to the caller;
     * if evaluation of this predicate throws an exception, the {@code other} predicate will
     * not be evaluated.
     *
     * @param other
     *            a predicate that will be logically-ANDed with this predicate
     * @return a composed predicate that represents the short-circuiting logical AND of
     *         this predicate and the {@code other} predicate
     * @throws NullPointerException
     *             if other is null
     */
    default SQLPredicate<T> and( final SQLPredicate<? super T> other )
    {
        Objects.requireNonNull(other);

        return t -> test(t) && other.test(t);
    }

    /**
     * Returns a predicate that represents the logical negation of this
     * predicate.
     *
     * @return a predicate that represents the logical negation of this
     * predicate
     */
    default SQLPredicate<T> negate()
    {
        return t -> !test(t);
    }

    /**
     * Returns a composed predicate that represents a short-circuiting logical
     * OR of this predicate and another.  When evaluating the composed
     * predicate, if this predicate is {@code true}, then the {@code other}
     * predicate is not evaluated.
     *
     * <p>Any exceptions thrown during evaluation of either predicate are relayed
     * to the caller; if evaluation of this predicate throws an exception, the
     * {@code other} predicate will not be evaluated.
     *
     * @param other a predicate that will be logically-ORed with this
     *              predicate
     * @return a composed predicate that represents the short-circuiting logical
     * OR of this predicate and the {@code other} predicate
     * @throws NullPointerException if other is null
     */
    default SQLPredicate<T> or( final SQLPredicate<? super T> other )
    {
        Objects.requireNonNull(other);

        return t -> test(t) || other.test(t);
    }

    /**
     * Returns a predicate that tests if two arguments are equal according to
     * {@link Objects#equals(Object, Object)}.
     *
     * @param <T>
     *            the type of arguments to the predicate
     * @param targetRef
     *            the object reference with which to compare for equality,
     *            which may be {@code null}
     * @return a predicate that tests if two arguments are equal according to
     *         {@link Objects#equals(Object, Object)}
     */
    static <T> SQLPredicate<T> isEqual( final Object targetRef )
    {
        return (null == targetRef)
                ? Objects::isNull
                : targetRef::equals; // object -> targetRef.equals(object) //
    }
}
