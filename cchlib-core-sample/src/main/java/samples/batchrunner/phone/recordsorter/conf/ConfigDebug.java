package samples.batchrunner.phone.recordsorter.conf;

import java.io.IOException;

public class ConfigDebug
{
    private ConfigDebug() {}

    public static void debug( Config config, Appendable out ) throws IOException
    {
        for( Contact c : config.getContacts() ) {
            out.append( "\t"  ).append( c.toString() );
            }
    }

    public static String toString( Config config )
    {
        StringBuilder sb = new StringBuilder();

        try {
            debug( config, sb );
            }
        catch( IOException e ) {
            throw new RuntimeException( e );
            }

        return sb.toString();
    }
}
