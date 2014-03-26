package com.googlecode.cchlib.tools.phone.recordsorter.conf.google;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import com.googlecode.cchlib.tools.phone.recordsorter.conf.AbstractConfigFactory;
import com.googlecode.cchlib.tools.phone.recordsorter.conf.Config;
import com.googlecode.cchlib.tools.phone.recordsorter.conf.ConfigFactory;
import com.googlecode.cchlib.xutil.google.googlecontact.GoogleContacts;

public class GoogleReadConfigFactory extends AbstractConfigFactory {
    private static GoogleReadConfigFactory INSTANCE;

    private GoogleReadConfigFactory() {}

    public static ConfigFactory getInstance()
    {
        if( INSTANCE == null ) {
            INSTANCE = new GoogleReadConfigFactory();
            }

        return INSTANCE;
    }

    @Override
    public Config load( final InputStream inStream ) throws IOException
    {
        return new GoogleConfigImpl().setList( GoogleContacts.createGoogleContacts( inStream ) );
    }

    @Override
    public Config newConfig() throws UnsupportedOperationException
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public void encodeToFile( final Config config, final File file )
        throws FileNotFoundException, IOException, UnsupportedOperationException
    {
        throw new UnsupportedOperationException();
    }

}
