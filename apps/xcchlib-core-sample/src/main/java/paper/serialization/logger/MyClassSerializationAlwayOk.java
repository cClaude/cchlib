package paper.serialization.logger;

import java.io.IOException;
import java.io.Serializable;
import org.apache.log4j.Logger;

public class MyClassSerializationAlwayOk extends SerialzationForTesting implements Serializable {
    private static final long serialVersionUID = 1L;

    private transient Logger  logger_;

    public MyClassSerializationAlwayOk( String aValue )
    {
        super( aValue );
    }


    @Override
    public void doJob()
    {
        getLogger().info( getMessage() );
    }

    private Logger getLogger()
    {
        if( logger_ == null ) {
            logger_ = Logger.getLogger( getClass() );
        }
        return logger_;
    }

    public static void main( final String... args ) throws ClassNotFoundException, IOException
    {
        testClone( new MyClassSerializationAlwayOk( "Test value" ), MyClassSerializationAlwayOk.class );
    }

}
