package cx.ath.choisnet.lang.reflect;

import java.util.Map;
import javax.swing.JLabel;
import org.apache.log4j.Logger;
import org.junit.Test;

/**
 *
 */
public class MappableHelperTest
{
    private final static Logger logger = Logger.getLogger( MappableHelperTest.class );

    @Test
    public void tstMappableJLabel()
    {
        JLabel				object 	= new JLabel( "testString" );
        Map<String, String> map 	= MappableHelper.toMap( object );

        for( Map.Entry<String,String> e : map.entrySet() ) {
            logger.info(
                object.getClass().getSimpleName()
                + "."
                + e.getKey()
                + " = "
                + e.getValue()
                );
        }
    }
}
