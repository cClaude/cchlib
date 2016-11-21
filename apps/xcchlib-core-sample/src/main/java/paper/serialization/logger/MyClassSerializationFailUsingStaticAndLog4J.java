package paper.serialization.logger;

import java.io.IOException;
import java.io.Serializable;
import org.apache.log4j.Logger;

public class MyClassSerializationFailUsingStaticAndLog4J extends SerialzationForTesting implements Serializable {
    private static final long serialVersionUID = 1L;
    private final Logger      logger           = Logger.getLogger( getClass() );

    public MyClassSerializationFailUsingStaticAndLog4J( final String aValue )
    {
        super( aValue );
    }

    @Override
    public void doJob()
    {
        logger.info( getMessage() );
    }

    public static void main( final String... args ) throws ClassNotFoundException, IOException
    {
        testClone( new MyClassSerializationFailUsingStaticAndLog4J( "Test value" ), MyClassSerializationFailUsingStaticAndLog4J.class );
    }
}
