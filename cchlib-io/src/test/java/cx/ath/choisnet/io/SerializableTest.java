package cx.ath.choisnet.io;

import java.io.IOException;
import org.junit.Test;
import com.googlecode.cchlib.test.SerializableTestCaseHelper;

/**
 * Verify that some objects are {@link Serializable}.
 */
public class SerializableTest
{
    @Test
    public void testBase() throws IOException, ClassNotFoundException
    {
        SerializableTestCaseHelper.testSerialization( Integer.valueOf( 15 ) );
        SerializableTestCaseHelper.testSerialization( "Serialization test" );
    }
}
