package cx.ath.choisnet.util.datetime;

import java.text.Format;

/**
 *
 * @author Claude CHOISNET
 *
 */
public interface DateTimeInterface
{
    public abstract String toString(Format format);

    public abstract long longValue();
}
