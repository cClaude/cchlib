package cx.ath.choisnet.util;

/**
 * Identify if an Object should be selected or not.
 *
 * @author Claude CHOISNET
 * @param <T>
 * @see cx.ath.choisnet.util.iterator.IteratorFilter
 * @see cx.ath.choisnet.util.impl.CollectionFilterImpl
 * 
 */
public interface Selectable<T>
{
    /**
     * Identify if giving object should be select
     * 
     * @param obj Object to test
     * @return true if object should be selected,
     *         false otherwise
     */
    public abstract boolean isSelected(T obj);
}
