package cx.ath.choisnet.util.datetime;

/**
 * 
 */
public interface DateInterface
    extends DateTimeInterface, Comparable<DateInterface>
{
    /**
     * 
     */
    public abstract boolean isBefore(DateInterface date);

    /**
     * 
     */
    public abstract boolean isAfter(DateInterface date);

    /**
     * 
     */
    public abstract DateInterface add(DateInterface date)
        throws BasicDateTimeException;

    /**
     * 
     */
    public abstract DateInterface sub(DateInterface date)
        throws BasicDateTimeException;

    /**
     *
     */
    public abstract boolean equals(DateInterface date);
}
