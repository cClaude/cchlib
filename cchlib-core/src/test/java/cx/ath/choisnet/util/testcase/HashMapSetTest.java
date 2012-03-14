package cx.ath.choisnet.util.testcase;

import java.util.Iterator;
import cx.ath.choisnet.util.HashMapSet;
import junit.framework.TestCase;

/**
 *
 */
public class HashMapSetTest extends TestCase
{
//    public final static String[] POPULATE = {
//
//    };

    public void test_StringString()
    {
        HashMapSet<String,String> hms  = new HashMapSet<String,String>();
        final int                 size = 10;

        for(int i=0;i<size;i++) {
            hms.add(
                    String.format( "key%d", i ),
                    String.format( "val%d", i )
                    );
        }
        assertEquals(size,hms.size());
        assertEquals(size,hms.valuesSize());
        assertEquals(size,sizeOfHashMapSet(hms));

        // Add same keys-values, no changes !
        for(int i=0;i<size;i++) {
            hms.add(
                    String.format( "key%d", i ),
                    String.format( "val%d", i )
                    );
        }
        assertEquals(size,hms.size());
        assertEquals(size,hms.valuesSize());

        // Add same keys, but with different values
        for(int i=0;i<size;i++) {
            hms.add(
                    String.format( "key%d", i ),
                    String.format( "XX%d", i )
                    );
        }
        assertEquals(size,hms.size());
        assertEquals(size*2,hms.valuesSize());
        assertEquals(size*2,sizeOfHashMapSet(hms));
    }


    private int sizeOfHashMapSet( HashMapSet<?,?> hms )
    {
        int         size = 0;
        Iterator<?> iter = hms.iterator();

        while(iter.hasNext()) {
            iter.next();
            size++;
        }
        return size;
    }


}
