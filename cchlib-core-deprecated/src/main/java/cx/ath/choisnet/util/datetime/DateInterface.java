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
    boolean isBefore(DateInterface date);

    /**
     *
     */
    boolean isAfter(DateInterface date);

    /**
     *
     */
    DateInterface add(DateInterface date)
        throws BasicDateTimeException;

    /**
     *
     */
    DateInterface sub(DateInterface date)
        throws BasicDateTimeException;

    /**
     *
     */
    boolean equals(DateInterface date);
}
