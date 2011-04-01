/**
 * 
 */
package cx.ath.choisnet.tools.i18n;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.Set;
import org.apache.log4j.Logger;

/**
 * Use standard Properties
 * 
 * @author Claude CHOISNET
 */
class CustomProperties 
    implements CustomPropertiesInterface 
{
    private static final long serialVersionUID = 1L;
    private static final Logger slogger = Logger.getLogger( CustomProperties.class );
    private Properties properties;
    private FileObject fileObject;
    
    public CustomProperties( 
            FileObject  fileObject,
            Properties  properties
            )
    {
        this.properties = properties;
        this.fileObject = fileObject;
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
                    + CompareRessourceBundleFrame.class
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

    @Override
    public String getProperty( String key )
    {
        return properties.getProperty( key );
    }

    @Override
    public void setProperty( String key, String value )
    {
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
