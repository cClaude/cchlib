package com.googlecode.cchlib.util;

import static org.junit.Assert.assertEquals;
import java.util.Iterator;
import org.junit.Test;

@SuppressWarnings("boxing")
public class LinkedHashMapListTest
{
	@Test
    public void test_StringString()
    {
        final LinkedHashMapList<String,String> hms  = new LinkedHashMapList<String,String>();
        final int                 keySize = 10;

        for(int i=0;i<keySize;i++) {
            hms.add(
                    String.format( "key%d", i ),
                    String.format( "val%d", i )
                    );
            }
        assertEquals(keySize,hms.keySize());
        assertEquals(keySize,hms.valuesSize());
        assertEquals(keySize,sizeOfHashMapSet(hms));

        // Add same keys-values, SIZE *= 2
        for(int i=0;i<keySize;i++) {
            hms.add(
                    String.format( "key%d", i ),
                    String.format( "val%d", i )
                    );
            }
        assertEquals(keySize,hms.keySize());
        assertEquals(keySize * 2,hms.valuesSize());
        assertEquals(keySize*2,sizeOfHashMapSet(hms));

        // Add same keys, but with different values
        for(int i=0;i<keySize;i++) {
            hms.add(
                    String.format( "key%d", i ),
                    String.format( "XX%d", i )
                    );
            }
        assertEquals(keySize,hms.keySize());
        assertEquals(keySize*3,hms.valuesSize());
        assertEquals(keySize*3,sizeOfHashMapSet(hms));
    }

    private int sizeOfHashMapSet(final LinkedHashMapList<?,?> hms)
    {
        int         size = 0;
        final Iterator<?> iter = hms.iterator();

        while(iter.hasNext()) {
            iter.next();
            size++;
            }
        return size;
    }

}
