package cx.ath.choisnet.util.iterator.iterable;

import java.util.Collection;

/**
 * TestCase
 * @deprecated
 */
@Deprecated
public class ArrayIteratorTest extends cx.ath.choisnet.util.iterator.iterable.IterableIteratorTestCaseHelper
{
  @Override
  protected <T> cx.ath.choisnet.util.iterator.iterable.IterableIterator<T> buildIterableIterator(
      final Collection<T> c1,
      final Collection<T> c2
      )
  {
    @SuppressWarnings("unchecked")
    T[] array = (T[]) new Object[c1.size() + c2.size()];

    cx.ath.choisnet.util.iterator.iterable.IterableIterator<T> bi = new cx.ath.choisnet.util.iterator.iterable.BiIterator<T>( c1, c2 );
    int       i  = 0;

    for(T t:bi) {
      array[ i++ ] = t;
    }

    return new cx.ath.choisnet.util.iterator.iterable.ArrayIterator<T>( array );
  }
}
