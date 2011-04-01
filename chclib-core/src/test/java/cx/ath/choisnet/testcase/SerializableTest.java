package cx.ath.choisnet.testcase;

import java.io.IOException;
import cx.ath.choisnet.test.SerializableTestCase;
import cx.ath.choisnet.util.datetime.BasicDate;
import cx.ath.choisnet.util.datetime.BasicTime;
import cx.ath.choisnet.util.datetime.TimePeriod;

/**
 *
 * @author Claude CHOISNET
 *
 */
public class SerializableTest extends SerializableTestCase
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
}
