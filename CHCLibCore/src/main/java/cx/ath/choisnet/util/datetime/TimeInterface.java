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
     * @param timeinterface
     * @return
     */
    public abstract boolean isBefore(TimeInterface timeinterface);

    /**
     * TODO: Doc!
     * 
     * @param timeinterface
     * @return
     */
    public abstract boolean isAfter(TimeInterface timeinterface);

    /**
     * TODO: Doc!
     * 
     * @param timeinterface
     * @return
     * @throws BasicDateTimeException
     */
    public abstract TimeInterface add(TimeInterface timeinterface)
        throws BasicDateTimeException;

    /**
     * TODO: Doc!
     * 
     * @param timeinterface
     * @return
     * @throws BasicDateTimeException
     */
    public abstract TimeInterface sub(TimeInterface timeinterface)
        throws BasicDateTimeException;

    /**
     * TODO: Doc!
     * 
     * @param timeinterface
     * @return
     */
    public abstract boolean equals(TimeInterface timeinterface);

    @Override
    public abstract int compareTo(TimeInterface timeinterface);
}
