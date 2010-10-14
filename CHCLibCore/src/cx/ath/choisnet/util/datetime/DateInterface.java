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
     * @param dateinterface
     * @return
     */
    public abstract boolean isBefore(DateInterface dateinterface);

    /**
     * 
     * @param dateinterface
     * @return
     */
    public abstract boolean isAfter(DateInterface dateinterface);

    /**
     * 
     * @param dateinterface
     * @return
     * @throws BasicDateTimeException
     */
    public abstract DateInterface add(DateInterface dateinterface)
        throws BasicDateTimeException;

    /**
     * 
     * @param dateinterface
     * @return
     * @throws BasicDateTimeException
     */
    public abstract DateInterface sub(DateInterface dateinterface)
        throws BasicDateTimeException;

    /**
     * 
     */
    public abstract int compareTo(DateInterface dateinterface);

    /**
     * @param dateinterface 
     * @return 
     */
    public abstract boolean equals(DateInterface dateinterface);
}
