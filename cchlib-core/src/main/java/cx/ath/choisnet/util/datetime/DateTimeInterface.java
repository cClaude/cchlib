package cx.ath.choisnet.util.datetime;

import java.text.Format;

/**
 * TODOC
 *
 */
public interface DateTimeInterface
{
    /**
     * 
     * @param format
     * @return TODOC
     */
    public abstract String toString(Format format);

    /**
     * 
     * @return TODOC
     */
    public abstract long longValue();
}
