/**
 * 
 */
package cx.ath.choisnet.tools.i18n;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.Set;
import org.apache.log4j.Logger;

/**
 * Use InputStream
 * 
 * @author Claude CHOISNET
 */
public class PropertiesStream implements CustomProperties 
{
    private static final long serialVersionUID = 1L;
    private static final Logger slogger = Logger.getLogger( PropertiesStream.class );
    private Properties properties;
    private FileObject fileObject;
    
    public PropertiesStream( 
            FileObject  fileObject
            )
    {
        this.properties = new Properties();
        this.fileObject = fileObject;
    }
    
    /* (non-Javadoc)
     * @see cx.ath.choisnet.tools.i18n.PropertiesInOut#load(cx.ath.choisnet.tools.i18n.FileObject)
     */
    @Override
    public void load(Set<String> keyBuilderSet) 
        throws FileNotFoundException, IOException
    {
        File file = fileObject.getFile();
        
        if( file != null ) {
            FileInputStream is = new FileInputStream(file);
            properties.load( is );
            is.close();

            keyBuilderSet.addAll( properties.stringPropertyNames() );
        }
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
            //keyBuilderSet ... ??? add empty ??
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

}
