package cx.ath.choisnet.io;

import java.io.IOException;
import org.junit.Test;
import cx.ath.choisnet.test.SerializableTestCaseHelper;

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
}
