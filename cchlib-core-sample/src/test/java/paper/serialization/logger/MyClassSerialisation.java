package paper.serialization.logger;

import java.io.IOException;
import java.io.NotSerializableException;
import org.junit.Assert;
import org.junit.Test;

public class MyClassSerialisation {

    @Test
    public void runTestsOK() throws ClassNotFoundException, IOException
    {
        Assert.assertTrue( SerialzationForTesting.testClone( new MyClassSerialisationOkUsingStatic( "Test MyClassSerialisationOkUsingStatic" ), MyClassSerialisationOkUsingStatic.class ) );
        Assert.assertTrue( SerialzationForTesting.testClone( new MyClassSerializationAlwayOk( "Test MyClassSerializationAlwayOk" ), MyClassSerializationAlwayOk.class ) );
        Assert.assertTrue( SerialzationForTesting.testClone( new MyClassSerializationOkUsingStaticAndSLF4J( "Test MyClassSerializationOkUsingStaticAndSLF4J" ), MyClassSerializationOkUsingStaticAndSLF4J.class ) );
    }

    @Test(expected=NotSerializableException.class)
    public void runTestKO() throws ClassNotFoundException, IOException
    {
        Assert.assertTrue( SerialzationForTesting.testClone( new MyClassSerializationFailUsingStaticAndLog4J( "Test MyClassSerializationFailUsingStaticAndLog4J" ), MyClassSerializationFailUsingStaticAndLog4J.class ) );
    }
}
