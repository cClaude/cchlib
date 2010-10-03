package cx.ath.choisnet.util.datetime;

/**
 *
 * @author Claude CHOISNET
 *
 */
public interface TimeInterface
    extends DateTimeInterface, Comparable<TimeInterface>
{
    public abstract boolean isBefore(TimeInterface timeinterface);

    public abstract boolean isAfter(TimeInterface timeinterface);

    public abstract TimeInterface add(TimeInterface timeinterface)
        throws BasicDateTimeException;

    public abstract TimeInterface sub(TimeInterface timeinterface)
        throws BasicDateTimeException;

    public abstract boolean equals(TimeInterface timeinterface);

    public abstract int compareTo(TimeInterface timeinterface);
}
