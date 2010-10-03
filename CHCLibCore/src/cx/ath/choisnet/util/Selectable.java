package cx.ath.choisnet.util;

/**
 *
 * @author Claude CHOISNET
 * @param <T> 
 *
 */
public interface Selectable<T>
{
    public abstract boolean isSelected(T obj);
}
