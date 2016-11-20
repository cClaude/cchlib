package com.googlecode.cchlib.tools.phone.recordsorter.conf;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public abstract class AbstractConfigFactory implements ConfigFactory {

    @Override
    public final Config load( final File file ) throws FileNotFoundException, IOException
    {
        try(  final InputStream inStream = new FileInputStream( file )) {
            return load( inStream );
        } catch( IOException e ) {
            throw new IOException( "Error while reading [" + file + ']', e );
        }
    }

}
