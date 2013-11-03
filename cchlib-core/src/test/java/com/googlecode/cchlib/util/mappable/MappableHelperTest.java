package com.googlecode.cchlib.util.mappable;

import java.util.Map;
import javax.swing.JLabel;
import org.apache.log4j.Logger;
import org.junit.Test;

/**
 *
 */
public class MappableHelperTest
{
    private static final Logger LOGGER = Logger.getLogger( MappableHelperTest.class );

    @Test
    public void tstMappableJLabel()
    {
        JLabel              object  = new JLabel( "testString" );
        Map<String, String> map     = MappableHelper.toMap( object );

        for( Map.Entry<String,String> e : map.entrySet() ) {
            LOGGER.info(
                object.getClass().getSimpleName()
                + "."
                + e.getKey()
                + " = "
                + e.getValue()
                );
        }
    }
}
