package cx.ath.choisnet.util.iterator.iterable;

import java.util.Iterator;

/**
 * An IterableIterator is an Iterator that could be restart/reset
 * using {@link Iterable#iterator()} method.
 * <br/>
 * These Iterator is design to avoid copy of
 * Collections in memory and to generate result
 * in the flow.
 *
 * @param <T> content type
 * @deprecated use {@link com.googlecode.cchlib.util.iterator.iterable.IterableIterator} instead
 */
@Deprecated
public interface IterableIterator<T>
    extends Iterable<T>,
            Iterator<T>, 
            com.googlecode.cchlib.util.iterator.iterable.IterableIterator<T>
{
}
