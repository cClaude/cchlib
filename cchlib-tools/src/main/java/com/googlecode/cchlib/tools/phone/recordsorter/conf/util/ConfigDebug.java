package com.googlecode.cchlib.tools.phone.recordsorter.conf.util;

import java.io.IOException;
import com.googlecode.cchlib.tools.phone.recordsorter.conf.Config;
import com.googlecode.cchlib.tools.phone.recordsorter.conf.Contact;

public class ConfigDebug
{
    private ConfigDebug() {}

    public static void debug( final Config config, final Appendable out ) throws IOException
    {
        for( final Contact c : config.getContacts() ) {
            out.append( '\t'  ).append( c.toString() );
            }
    }

    public static String toString( final Config config )
    {
        final StringBuilder sb = new StringBuilder();

        try {
            debug( config, sb );
            }
        catch( IOException e ) {
            throw new RuntimeException( e );
            }

        return sb.toString();
    }
}
