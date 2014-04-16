package paper.serialization.logger;

import java.io.IOException;
import java.io.Serializable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyClassSerializationOkUsingStaticAndSLF4J extends SerialzationForTesting implements Serializable {
    private static final long serialVersionUID = 1L;

    private final Logger      logger           = LoggerFactory.getLogger( getClass() );

    public MyClassSerializationOkUsingStaticAndSLF4J( final String aValue )
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
        testClone( new MyClassSerializationOkUsingStaticAndSLF4J( "Test value" ), MyClassSerializationOkUsingStaticAndSLF4J.class );
    }
}
