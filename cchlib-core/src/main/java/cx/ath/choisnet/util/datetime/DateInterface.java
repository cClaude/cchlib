package cx.ath.choisnet.util.datetime;

/**
 * TODOC
 *
 * @author Claude CHOISNET
 */
public interface DateInterface
    extends DateTimeInterface, Comparable<DateInterface>
{
    /**
     * TODOC
     *
     * @param date
     * @return true if date is before current date
     */
    public abstract boolean isBefore(DateInterface date);

    /**
     * TODOC
     *
     * @param date
     * @return true if date is after current date
     */
    public abstract boolean isAfter(DateInterface date);

    /**
     * TODOC
     *
     * @param date
     * @return TODOC
     * @throws BasicDateTimeException
     */
    public abstract DateInterface add(DateInterface date)
        throws BasicDateTimeException;

    /**
     * TODOC
     *
     * @param date
     * @return TODOC
     * @throws BasicDateTimeException
     */
    public abstract DateInterface sub(DateInterface date)
        throws BasicDateTimeException;

//    /**
//     * TO DO: Doc!
//     *
//     * @param date
//     * @return TO DO doc!
//     */
//    public abstract int compareTo(DateInterface date);

    /**
     * TODOC
     *
     * @param date
     * @return TODOC
     */
    public abstract boolean equals(DateInterface date);
}
