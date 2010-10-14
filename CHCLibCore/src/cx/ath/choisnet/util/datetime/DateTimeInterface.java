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
     * @return
     */
    public abstract String toString(Format format);

    /**
     * 
     * @return
     */
    public abstract long longValue();
}
