/**
 * 
 */
package cx.ath.choisnet.i18n.builder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.EnumSet;
import org.apache.log4j.Logger;
import cx.ath.choisnet.i18n.AutoI18nExceptionHandler;
import cx.ath.choisnet.i18n.I18nInterface;

/***
 * TODO: Doc! (sample)
 * 
 * <p><b>IMPORTANT:</b></br> 
 * Result is write into your classes directory!
 * </p>
 * 
 * @author Claude CHOISNET
 */
public class I18nPropertyResourceBundleAutoUpdate 
    extends AbstractI18nPropertiesResourceAutoUpdate
{
    private static Logger slogger = Logger.getLogger(I18nPropertyResourceBundleAutoUpdate.class);
    
    /**
     * @param i18n
     */
    public I18nPropertyResourceBundleAutoUpdate( 
            I18nAutoUpdateInterface i18n
            )
    {
        super( i18n, null, null );
    }
    
    /**
     * @param i18nAutoUpdateInterface
     * @param handler 
     * @param attributes 
     */
    public I18nPropertyResourceBundleAutoUpdate( 
            I18nAutoUpdateInterface     i18nAutoUpdateInterface,
            AutoI18nExceptionHandler    handler,
            EnumSet<Attrib>             attributes
            )
    {
        super(i18nAutoUpdateInterface,handler,attributes);
    }
    
    /**
     * Support only {@link I18nAutoUpdateInterface}
     */
    @Override
    public void setI18n( final I18nInterface i18nObject )
    {
        if( !( i18nObject instanceof I18nAutoUpdateInterface) ) {
            throw new RuntimeException(
                    "I18nInterface must be I18nAutoUpdateInterface"
                    );
        }
        I18nAutoUpdateInterface i18nAutoUpdateInterface = I18nAutoUpdateInterface.class.cast(i18nObject);
        
        super.setI18nAutoUpdateInterface( i18nAutoUpdateInterface );
    }

    private URL getResourceBundleBaseNameURL()
    {
        String fixName = getI18nAutoUpdateInterface().getResourceBundleBaseName()
                .replace( '.', '/' )
                + ".properties";
        
        return getClass().getClassLoader().getResource( fixName );
    }

    @Override
    public InputStream getResourceBundleInputStream() throws IOException
    {
        URL url = getResourceBundleBaseNameURL();
        
        slogger.info( "getResourceBundleInputStream() read from: " + url );
        
        return url.openStream();
    }

    /**
     * Can't be override to use how own OutputStream
     * @return an InputStream, may be null
     * @throws IOException
     */
    public OutputStream getResourceBundleOutputStream() throws IOException
    {
        URL url = getResourceBundleBaseNameURL();
        URI uri;
        
        try {
            uri = url.toURI();
        }
        catch( URISyntaxException e ) {
            throw new IOException("URISyntaxException for:" + url, e);
        }
        
        File file = new File( uri );
        
        slogger.info( "getResourceBundleOutputStream() write to: " + file );

        return new FileOutputStream( file );
    }
}
