package cx.ath.choisnet.util.datetime;

/**
 * TODO: Doc!
 *
 * @author Claude CHOISNET
 */
public interface TimeInterface
    extends DateTimeInterface, Comparable<TimeInterface>
{
    /**
     * TODO: Doc!
     *
     * @param time
     * @return true if time is before current time
     */
    public abstract boolean isBefore(TimeInterface time);

    /**
     * TODO: Doc!
     *
     * @param time
    * @return true if time is after current time
     */
    public abstract boolean isAfter(TimeInterface time);

    /**
     * TODO: Doc!
     *
     * @param time
     * @return TODO doc!
     * @throws BasicDateTimeException
     */
    public abstract TimeInterface add(TimeInterface time)
        throws BasicDateTimeException;

    /**
     * TODO: Doc!
     *
     * @param time
     * @return TODO doc!
     * @throws BasicDateTimeException
     */
    public abstract TimeInterface sub(TimeInterface time)
        throws BasicDateTimeException;

    /**
     * TODO: Doc!
     *
     * @param time
     * @return TODO doc!
     */
    public abstract boolean equals(TimeInterface time);

    //@Override
    //public abstract int compareTo(TimeInterface time);
}
