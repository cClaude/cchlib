package com.googlecode.cchlib.util;

import java.util.Iterator;
import org.junit.Assert;
import org.junit.Test;

public class HashMapSetTest
{
    @SuppressWarnings("boxing")
    @Test
    public void test_StringString()
    {
        final HashMapSet<String,String> hms  = new HashMapSet<String,String>();
        final int                       size = 10;

        for(int i=0;i<size;i++) {
            hms.add(
                    String.format( "key%d", i ),
                    String.format( "val%d", i )
                    );
        }
        Assert.assertEquals(size,hms.size());
        Assert.assertEquals(size,hms.valuesSize());
        Assert.assertEquals(size,sizeOfHashMapSet(hms));

        // Add same keys-values, no changes !
        for(int i=0;i<size;i++) {
            hms.add(
                    String.format( "key%d", i ),
                    String.format( "val%d", i )
                    );
        }
        Assert.assertEquals(size,hms.size());
        Assert.assertEquals(size,hms.valuesSize());

        // Add same keys, but with different values
        for(int i=0;i<size;i++) {
            hms.add(
                    String.format( "key%d", i ),
                    String.format( "XX%d", i )
                    );
        }
        Assert.assertEquals(size,hms.size());
        Assert.assertEquals(size*2,hms.valuesSize());
        Assert.assertEquals(size*2,sizeOfHashMapSet(hms));
    }

    private int sizeOfHashMapSet( final HashMapSet<?,?> hms )
    {
        int               size = 0;
        final Iterator<?> iter = hms.iterator();

        while(iter.hasNext()) {
            iter.next();
            size++;
            }

        return size;
    }
}
