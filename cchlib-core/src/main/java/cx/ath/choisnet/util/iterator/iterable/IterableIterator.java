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
 */
public interface IterableIterator<T>
    extends Iterable<T>, Iterator<T>
{
}
