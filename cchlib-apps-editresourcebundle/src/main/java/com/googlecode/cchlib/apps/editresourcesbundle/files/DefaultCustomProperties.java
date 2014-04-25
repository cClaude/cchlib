package com.googlecode.cchlib.apps.editresourcesbundle.files;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Set;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.apps.editresourcesbundle.compare.CompareResourcesBundleFrame;


/**
 * Use standard Properties
 */
public class DefaultCustomProperties
    extends AbstractCustomProperties
{
    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logger.getLogger( DefaultCustomProperties.class );

    private Properties properties;
    private FileObject fileObject;

    public DefaultCustomProperties(
        final FileObject  fileObject,
        final Properties  defaults
        ) throws FileNotFoundException, IOException
    {
        this.fileObject = fileObject;
        this.properties = new Properties( defaults );

        InputStream is = new FileInputStream( fileObject.getFile() );

        try {
            properties.load( is );
            }
        finally {
            is.close();
            }

        this.fileObject.setCustomProperties( this );
    }

    @Override
    public Set<String> stringPropertyNames()
    {
        return properties.stringPropertyNames();
    }

    @Override
    public boolean store() throws FileNotFoundException, IOException
    {
        if( fileObject.isReadOnly() ) {
            LOGGER.warn( "Can't save (readOnly): " + fileObject );

            return false;
            }
        else {
            return storeAs( fileObject.getFile() );
            }
    }

    @Override
    public boolean storeAs( final File file ) throws FileNotFoundException, IOException
    {
        final String comment = file.getPath();

        try( final FileOutputStream os = new FileOutputStream( file ) ) {
            properties.store(
                    os,
                    "Created by "
                        + CompareResourcesBundleFrame.class
                        + " :"
                        + comment
                    );
            }

        LOGGER.info( "Save : " + file );

        return true;
    }

    @Override
    public FileObject getFileObject()
    {
        return fileObject;
    }

    public Properties getProperties()
    {
        return properties;
    }

    @Override
    public String getProperty( final String key )
    {
        return properties.getProperty( key );
    }

    @Override
    public void setProperty( final String key, final String value )
    {
        setEdited( true );
        properties.setProperty( key, value );
    }

    @Override
    public boolean isLinesNumberHandle()
    {
        return false;
    }

    @Override
    public int getLineNumber( final String key )
    {
        throw new UnsupportedOperationException();
    }
}
