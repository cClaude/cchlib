package cx.ath.choisnet.util.datetime;

/**
 *
 * @author Claude CHOISNET
 */
public interface DateInterface
    extends DateTimeInterface, Comparable<DateInterface>
{
    /**
     * 
     * @param date
     * @return true if date is before current date
     */
    public abstract boolean isBefore(DateInterface date);

    /**
     * 
     * @param date
     * @return true if date is after current date
     */
    public abstract boolean isAfter(DateInterface date);

    /**
     * 
     * @param date
     * @return TODO doc!
     * @throws BasicDateTimeException
     */
    public abstract DateInterface add(DateInterface date)
        throws BasicDateTimeException;

    /**
     * 
     * @param date
     * @return TODO doc!
     * @throws BasicDateTimeException
     */
    public abstract DateInterface sub(DateInterface date)
        throws BasicDateTimeException;

    /**
     * @param date
     * @return TODO doc!
     */
    public abstract int compareTo(DateInterface date);

    /**
     * @param date
     * @return TODO doc!
     */
    public abstract boolean equals(DateInterface date);
}
