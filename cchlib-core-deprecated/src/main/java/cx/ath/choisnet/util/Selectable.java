package cx.ath.choisnet.util;

/**
 * Identify if an Object should be selected or not.
 *
 * @param <T> content type
 * @see com.googlecode.cchlib.util.iterator.IteratorFilter
 * @see cx.ath.choisnet.util.impl.CollectionFilterImpl
 * @deprecated use {@link com.googlecode.cchlib.util.iterator.Selectable} instead
 */
public interface Selectable<T> extends com.googlecode.cchlib.util.iterator.Selectable<T>
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
