package cx.ath.choisnet.util.datetime;

/**
 *
 * @author Claude CHOISNET
 *
 */
public interface DateInterface
    extends DateTimeInterface, Comparable<DateInterface>
{
    public abstract boolean isBefore(DateInterface dateinterface);

    public abstract boolean isAfter(DateInterface dateinterface);

    public abstract DateInterface add(DateInterface dateinterface)
        throws cx.ath.choisnet.util.datetime.BasicDateTimeException;

    public abstract DateInterface sub(DateInterface dateinterface)
        throws cx.ath.choisnet.util.datetime.BasicDateTimeException;

    public abstract int compareTo(DateInterface dateinterface);

    public abstract boolean equals(DateInterface dateinterface);
}
