package cx.ath.choisnet.util.iterator.iterable.testcase;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import org.apache.log4j.Logger;
import cx.ath.choisnet.util.iterator.iterable.BiIterator;
import junit.framework.TestCase;

/**
 * TestCase
 *
 * @author Claude CHOISNET
 *
 */
public class BiIteratorTest extends TestCase
{
    final private static Logger slogger = Logger.getLogger(BiIteratorTest.class);

    private final static Collection<Integer> getCollection(int...values)
    {
        List<Integer> c = new ArrayList<Integer>(values.length);

        for(int i:values) {
            c.add(new Integer( i ));
            }

        return c;
    }
    private final static Collection<Integer> getCollectionEmpty()
    {
        return new ArrayList<Integer>(0);
    }
    private final static Collection<Integer> getCollection1()
    {
        return getCollection( 0, 1, 2, 3 );
    }
    private final static Collection<Integer> getCollection2()
    {
        return getCollection( 4, 5, 6 );
    }
    private <T> void test(Collection<T> c1, Collection<T> c2)
    {
        int             total  = c1.size() + c2.size();
        BiIterator<T>   iter   = new BiIterator<T>( c1, c2 );
        int             count  = test( iter );

        assertEquals("Not same size !", total, count);

        count = test( iter );

        assertEquals("Iterator already read (no more element)", 0, count);

        count = test( iter.iterator() );

        assertEquals("Not same size !", total, count);
    }

    private <T> int test(Iterator<T> iter)
    {
        int  count = 0;

        while( iter.hasNext() ) {
            iter.next();
            count++;
        }

        return count;
    }

    public void test_crash1()
    {
        try {
            new BiIterator<Integer>( null, getCollectionEmpty() );
            
            super.fail( "Should crash");
        }
        catch( NullPointerException e ) {
            slogger.info( "Crash ok" );
        }
    }
    public void test_crash2()
    {
        try {
            new BiIterator<Integer>( getCollectionEmpty(), null );
            
            super.fail( "Should crash");
        }
        catch( NullPointerException e ) {
            slogger.info( "Crash ok" );
        }
    }

    public void test1()
    {
        test( getCollectionEmpty(), getCollectionEmpty() );
    }
    
    public void test2()
    {
        test( getCollection1(), getCollection2() );
    }
}