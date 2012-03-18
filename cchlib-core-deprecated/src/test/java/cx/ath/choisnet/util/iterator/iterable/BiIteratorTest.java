package cx.ath.choisnet.util.iterator.iterable;

import java.util.Collection;

/**
 * TestCase
 * @deprecated
 */
@Deprecated
public class BiIteratorTest extends cx.ath.choisnet.util.iterator.iterable.IterableIteratorTestCaseHelper
{
  @Override
  protected <T> cx.ath.choisnet.util.iterator.iterable.IterableIterator<T> buildIterableIterator(
      final Collection<T> c1,
      final Collection<T> c2
      )
  {
    return new cx.ath.choisnet.util.iterator.iterable.BiIterator<T>( c1, c2 );
  }
}
