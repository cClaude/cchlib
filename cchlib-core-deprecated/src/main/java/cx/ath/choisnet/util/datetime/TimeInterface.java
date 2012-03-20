package cx.ath.choisnet.util.datetime;

/**
 * 
 *
 */
public interface TimeInterface
    extends DateTimeInterface, Comparable<TimeInterface>
{
    /**
     * 
     *
     */
    public abstract boolean isBefore(TimeInterface time);

    /**
     * 
     *
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
     * 
     */
    public abstract boolean equals(TimeInterface time);
}
