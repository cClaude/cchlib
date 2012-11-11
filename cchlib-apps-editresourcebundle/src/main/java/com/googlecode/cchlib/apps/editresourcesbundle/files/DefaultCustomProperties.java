package com.googlecode.cchlib.apps.editresourcesbundle.files;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.Set;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.apps.editresourcesbundle.CompareResourcesBundleFrame;


/**
 * Use standard Properties
 *
 */
public
class DefaultCustomProperties
    extends AbstractCustomProperties
{
    private static final long serialVersionUID = 1L;
    private static final Logger slogger = Logger.getLogger( DefaultCustomProperties.class );
    private Properties properties;
    private FileObject fileObject;

    public DefaultCustomProperties(
        final FileObject  fileObject,
        final Properties  defaults
        ) throws FileNotFoundException, IOException
    {
        this.fileObject = fileObject;
        this.properties = new Properties( defaults );

        properties.load(
              new FileInputStream(
                  fileObject.getFile()
                  )
              );
        
        this.fileObject.setCustomProperties( this );
    }

    @Override
    public Set<String> stringPropertyNames()
    {
        return properties.stringPropertyNames();
    }

    /* (non-Javadoc)
     * @see cx.ath.choisnet.tools.i18n.PropertiesInOut#store(cx.ath.choisnet.tools.i18n.FileObject)
     */
    @Override
    public boolean store()
        throws FileNotFoundException, IOException
    {
        if( fileObject.isReadOnly() ) {
            slogger.warn( "Can't save (readOnly): " + fileObject );

            return false;
       }
        else {
            String              comment = fileObject.getFile().getPath();
            FileOutputStream    os      = new FileOutputStream(fileObject.getFile());

            properties.store(
                os,
                "Creat by "
                    + CompareResourcesBundleFrame.class
                    + " :"
                    + comment
                );
            os.close();
            slogger.info( "Save : " + fileObject );

            return true;
        }
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
    public String getProperty( String key )
    {
        return properties.getProperty( key );
    }

    @Override
    public void setProperty( String key, String value )
    {
        setEdited( true );
        properties.setProperty( key, value );
    }

    @Override
    public boolean handleLinesNumbers()
    {
        return false;
    }

    @Override
    public int getLineNumber( String key )
    {
        return 0;
    }
}
