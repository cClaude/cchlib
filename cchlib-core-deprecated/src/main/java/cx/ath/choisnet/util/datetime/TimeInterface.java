package cx.ath.choisnet.util.datetime;

/**
 * TODOC
 *
 * @author Claude CHOISNET
 */
public interface TimeInterface
    extends DateTimeInterface, Comparable<TimeInterface>
{
    /**
     * TODOC
     *
     * @param time
     * @return true if time is before current time
     */
    public abstract boolean isBefore(TimeInterface time);

    /**
     * TODOC
     *
     * @param time
    * @return true if time is after current time
     */
    public abstract boolean isAfter(TimeInterface time);

//    /**
//     * TO DOC
//     *
//     * @param time
//     * @return TOD O doc!
//     * @throws BasicDateTimeException
//     */
//    public abstract TimeInterface add(TimeInterface time)
//        throws BasicDateTimeException;
//
//    /**
//     * TO DOC
//     *
//     * @param time
//     * @return TOD O doc!
//     * @throws BasicDateTimeException
//     */
//    public abstract TimeInterface sub(TimeInterface time)
//        throws BasicDateTimeException;

    /**
     * TODOC
     *
     * @param time
     * @return TODOC
     */
    public abstract boolean equals(TimeInterface time);
}
