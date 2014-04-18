package cx.ath.choisnet.io;

import com.googlecode.cchlib.test.SerializableTestCaseHelper;
import java.io.IOException;
import org.junit.Test;

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
