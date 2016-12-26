package cx.ath.choisnet.util.datetime;

/**
 *
 * @param <T> NEEDDOC
 * @since 1.53.014
 */
public interface TimeInterface<T extends TimeInterface<T>>
    extends DateTimeInterface, Comparable<T>
{
    boolean isBefore( T anotherTime );
    boolean isAfter( T anotherTime );

    T add( T anotherTime ) throws BasicDateTimeException;
    T sub( T anotherTime ) throws BasicDateTimeException;

    /**
     * Compare with an other {@link TimeInterface}
     *
     * @param anotherTime
     *            an other {@link TimeInterface}
     * @return true if both {@link TimeInterface} are equals
     */
    boolean isEqualTo( T anotherTime );

    /**
     * {@inheritDoc}
     *
     * Compare with an other {@link TimeInterface}
     *
     * @param anotherTime an other {@link TimeInterface}
     * @return 0 if both {@link TimeInterface} are equals, a negative value
     *         if current object is older than {@code anotherTime}.
     *         A positive value if current object is younger than
     *        {@code anotherTime}.
     *
     */
    @Override
    int compareTo( T anotherTime );
}

