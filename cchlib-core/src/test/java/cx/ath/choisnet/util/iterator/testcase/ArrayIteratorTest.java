package cx.ath.choisnet.util.iterator.testcase;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import org.apache.log4j.Logger;
import cx.ath.choisnet.util.iterator.ArrayIterator;
import junit.framework.TestCase;

/**
 * TestCase
 * 
 * @author Claude CHOISNET
 *
 */
public class ArrayIteratorTest extends TestCase
{
    final private static Logger slogger = Logger.getLogger(ArrayIteratorTest.class);

    private final static Integer[] ARRAY_INT = {
        1,2,3,4,5,6
    };
    private final static List<?>[] ARRAY_LIST = {
        buildList(1),
        buildList(2),
        buildList(3),
        buildList(4)
    };

    public void test1()
    {
        ArrayIterator<Integer> iter  = new ArrayIterator<Integer>(ARRAY_INT);
        int                    count = 0;
        
        while( iter.hasNext() ) {
            iter.next();
            count++;
        }
        
        assertEquals("Not same size !", ARRAY_INT.length, count);
    }
    
    public void test2()
    {
        final int offset = 2;
        final int len    = 3;
        ArrayIterator<Integer> iter  = new ArrayIterator<Integer>(ARRAY_INT,offset,len);
        int                    count = 0;
        Integer                firstValue = null;
        
        while( iter.hasNext() ) {
            int v = iter.next();
            
            if( firstValue == null ) {
                firstValue = v;
            }
            
            count++;
        }
        
        assertEquals("bad size !", len, count);
        assertEquals("bad first value !", ARRAY_INT[offset], firstValue);
    }
    
    public void test3()
    {
        final int offset = 2;
        final int len    = ARRAY_INT.length - offset;
        ArrayIterator<Integer> iter  = new ArrayIterator<Integer>(ARRAY_INT,offset,len);
        int                    count = 0;

        while( iter.hasNext() ) {
            iter.next();
            count++;
        }
        
        assertEquals("bad size !", len, count);
    }
    
    public void testBadLen()
    {
        final int offset = 2;
        final int len    = ARRAY_INT.length - offset + 1;
        ArrayIterator<Integer> iter  = new ArrayIterator<Integer>(ARRAY_INT,offset,len);
        int                    count = 0;
        
        try {
            while( iter.hasNext() ) {
                iter.next();
                count++;
            }
            fail( "Should fail" );
        }
        catch( NoSuchElementException ok ) {
            //ok
        }
        
        assertEquals("bad size !", ARRAY_INT.length - offset, count);
    }
    
    public void test_BuildFromItems()
    {
        test_BuildFromItems(Integer.class,ARRAY_INT);
        test_BuildFromItems(List.class,ARRAY_LIST);

        Iterator<Integer> iter = ArrayIterator.of( new Integer(1), new Integer(2), new Integer(3) );
        test_BuildFromItems( Integer.class, iter, 3 );
    }

    public <T> void test_BuildFromItems( Class<T> clazz, T[] items)
    {
        ArrayIterator<T> iter = new ArrayIterator<T>(clazz, items[0], items[1]);
        test_BuildFromItems( clazz, iter, 2 );

        iter = new ArrayIterator<T>(clazz, items[0], items[1], items[2]);
        test_BuildFromItems( clazz, iter, 3 );
    }
    
    public <T> void test_BuildFromItems( Class<T> clazz, Iterator<T> iter, int xCount)
    {
        int count = 0;

        while( iter.hasNext() ) {
            T item = iter.next();
            count++;
            slogger.info( "T class: " + item.getClass() + " value: " + item);

            assertTrue("Type look bad!", clazz.isAssignableFrom( item.getClass() ) );
        }
        
        assertEquals( "Bad size !", xCount, count);
    }

    private static List<String> buildList(int v)
    {
        List<String> lst = new ArrayList<String>(v);
        
        for( int i = 0;i<v;i++) {
            lst.add( Integer.toString( i ) );
        }
        
        return lst;
    }
}