package cx.ath.choisnet.util.datetime;

import java.text.Format;

/**
 *
 * @author Claude CHOISNET
 *
 */
public interface DateTimeInterface
{
    /**
     * 
     * @param format
     * @return TODO doc!
     */
    public abstract String toString(Format format);

    /**
     * 
     * @return TODO doc!
     */
    public abstract long longValue();
}
