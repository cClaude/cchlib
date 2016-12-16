package com.googlecode.cchlib.util.mappable;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.junit.Assume.assumeTrue;
import java.util.Map;
import javax.swing.JLabel;
import org.apache.log4j.Logger;
import org.junit.Test;
import com.googlecode.cchlib.swing.SafeSwingUtilities;

public class MappableHelperTest
{
    private static final Logger LOGGER = Logger.getLogger( MappableHelperTest.class );

    @Test
    public void tstMappableJLabel()
    {
        // Stop if GUI usage is not allowed
        assumeTrue( SafeSwingUtilities.isSwingAvailable() );

        final JLabel              object  = new JLabel( "testString" );
        final Map<String, String> map     = MappableHelper.toMap( object );

        for( final Map.Entry<String,String> entry : map.entrySet() ) {
            LOGGER.info(
                object.getClass().getSimpleName()
                + "."
                + entry.getKey()
                + " = "
                + entry.getValue()
                );
        }

        assertThat( map ).as( "Should have values" ).isNotEmpty();

        assertThat( map )
            .as( "Missign key : getClass()" )
            .containsKey( "getClass()" );

        assertThat( map.get( "getClass()" ) )
            .as( "Unespected value for : getClass()" )
            .isEqualTo( "class javax.swing.JLabel" );
    }
}
