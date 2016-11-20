package paper.serialization.logger;

import java.io.IOException;
import java.io.Serializable;
import org.apache.log4j.Logger;


public class MyClassSerialisationOkUsingStatic extends SerialzationForTesting implements Serializable {
    private static final long   serialVersionUID = 1L;

    //private static final Logger LOGGER  = LoggerFactory.getLogger( MyClassSerialisationOkUsingStatic.class );
    private static final Logger LOGGER  = Logger.getLogger( MyClassSerialisationOkUsingStatic.class );

    public MyClassSerialisationOkUsingStatic( final String aValue )
    {
        super( aValue );
    }

    @Override
    public void doJob()
    {
        LOGGER.info( getMessage() );
    }

    public static void main( final String... args ) throws ClassNotFoundException, IOException
    {
        testClone(  new MyClassSerialisationOkUsingStatic( "Test value" ), MyClassSerialisationOkUsingStatic.class );
    }
}
