package cx.ath.choisnet.test;

import java.io.IOException;
import java.io.Serializable;
import cx.ath.choisnet.io.SerializableHelper;
import cx.ath.choisnet.util.datetime.BasicDate;
import cx.ath.choisnet.util.datetime.BasicTime;
import cx.ath.choisnet.util.datetime.TimePeriod;
import junit.framework.TestCase;

/**
 *
 * @author Claude CHOISNET
 *
 */
public class TestSerializable extends TestCase
{
    //final private static Logger slogger = Logger.getLogger(TestSerializable.class);

    public void testBase() throws IOException, ClassNotFoundException
    {
        testSerialization(new Integer( 15 ));
        testSerialization("Serialization test");
    }
    
    public void testDateTime() throws IOException, ClassNotFoundException
    {
        testSerialization(new BasicTime());
        testSerialization(new BasicDate());
        testSerialization(new TimePeriod(123456789));
        
    }
    
    public static <T extends Serializable> T testSerialization(
            T anObject
            ) 
        throws IOException, ClassNotFoundException
    {
        T copy = cloneOverSerialization(anObject);

        assertEquals( "Values do not matches", anObject, copy);
        assertFalse( "Must be not same Object !", anObject==copy);
        
        return copy;
    }

    public static <T extends Serializable> T cloneOverSerialization(
            T anObject
            )
        throws java.io.IOException, ClassNotFoundException
    {
        @SuppressWarnings("unchecked")
        Class<T> clazz = (Class<T>)anObject.getClass();
        
        return SerializableHelper.clone( anObject, clazz );
    }
}
