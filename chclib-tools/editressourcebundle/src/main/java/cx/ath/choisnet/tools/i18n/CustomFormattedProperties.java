/**
 * 
 */
package cx.ath.choisnet.tools.i18n;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Set;
import org.apache.log4j.Logger;
import cx.ath.choisnet.util.FormattedProperties;

/**
 * Use InputStream
 * 
 * @author Claude CHOISNET
 */
class CustomFormattedProperties 
    implements CustomPropertiesInterface 
{
    private static final long serialVersionUID = 1L;
    private static final Logger slogger = Logger.getLogger( CustomPropertiesInterface.class );
    private FileObject          fileObject;
    private FormattedProperties properties;
    private HashMap<String,Integer> linesNumbers;
   
    public CustomFormattedProperties( 
            FileObject          fileObject,
            FormattedProperties properties
            )
    {
        this.fileObject     = fileObject;
        this.properties     = properties;
        this.linesNumbers   = new HashMap<String,Integer>();
        
        refreshLinesNumber();
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
            FileWriter out = new FileWriter(
                    fileObject.getFile()
                    );

            properties.store( out, "" );

            out.close();
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
        return true;
    }

    @Override
    public int getLineNumber( String key )
    {
        Integer v = this.linesNumbers.get( key );
       
        if( v != null ) {
            return v.intValue();
        }
        else {
            return 0;
        }
    }

    private void refreshLinesNumber()
    {
        this.linesNumbers.clear();
        int lineNumber = 1;
        
        for(FormattedProperties.Line line:properties.getLines()) {
            if( ! line.isComment() ) {
                this.linesNumbers.put( line.getKey(), lineNumber );
            }
            lineNumber++;
        }
    }
}
