package cx.ath.choisnet.util.datetime;

import java.text.Format;

/**
 *
 * @since 1.53.014
 */
@SuppressWarnings("squid:S1609") // Sonar is stupid, only one method for sonar plug-in here.
public interface DateTimeInterface
{
    /**
     * Returns {@link DateTimeInterface} as a formatted String.
     *
     * @param formatter
     *            Expected format
     * @return a formatted String.
     *
     * @see java.text.Format
     * @see java.text.DateFormat
     * @see java.text.SimpleDateFormat
     */
    String toString( Format formatter );

    /**
     * @return value has a long
     */
    long longValue();
}
