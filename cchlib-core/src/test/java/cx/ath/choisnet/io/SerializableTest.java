package cx.ath.choisnet.io;

import java.io.IOException;
import org.junit.Test;
import cx.ath.choisnet.test.SerializableTestCaseHelper;
import cx.ath.choisnet.util.datetime.BasicDate;
import cx.ath.choisnet.util.datetime.BasicTime;
import cx.ath.choisnet.util.datetime.TimePeriod;

/**
 * Verify that some objects are {@link Serializable}.
 */
public class SerializableTest
{
    @Test
    public void testBase() throws IOException, ClassNotFoundException
    {
        SerializableTestCaseHelper.testSerialization(new Integer( 15 ));
        SerializableTestCaseHelper.testSerialization("Serialization test");
    }

    @Test
    public void testDateTime() throws IOException, ClassNotFoundException
    {
        SerializableTestCaseHelper.testSerialization(new BasicTime());
        SerializableTestCaseHelper.testSerialization(new BasicDate());
        SerializableTestCaseHelper.testSerialization(new TimePeriod(123456789));
    }
}
