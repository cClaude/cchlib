package cx.ath.choisnet.util.iterator.iterable.testcase;

import java.util.Collection;

import cx.ath.choisnet.util.iterator.iterable.ArrayIterator;
import cx.ath.choisnet.util.iterator.iterable.BiIterator;
import cx.ath.choisnet.util.iterator.iterable.IterableIterator;

/**
 * TestCase
 *
 * @author Claude CHOISNET
 *
 */
public class ArrayIteratorTest extends IterableIteratorTestCaseHelper
{
  @Override
  protected <T> IterableIterator<T> buildIterableIterator(
      final Collection<T> c1,
      final Collection<T> c2
      )
  {
    @SuppressWarnings("unchecked")
    T[] array = (T[]) new Object[c1.size() + c2.size()];

    BiIterator<T> bi = new BiIterator<T>( c1, c2 );
    int       i  = 0;

    for(T t:bi) {
      array[ i++ ] = t;
    }

    return new ArrayIterator<T>( array );
  }
}