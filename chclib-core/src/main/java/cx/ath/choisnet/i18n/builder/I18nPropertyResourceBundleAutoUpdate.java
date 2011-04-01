/**
 *
 */
package cx.ath.choisnet.i18n.builder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.EnumSet;
import org.apache.log4j.Logger;
import cx.ath.choisnet.i18n.AutoI18n;
import cx.ath.choisnet.i18n.AutoI18nEventHandler;
import cx.ath.choisnet.i18n.AutoI18nExceptionHandler;
import cx.ath.choisnet.i18n.AutoI18nTypes;
import cx.ath.choisnet.i18n.I18nInterface;

/***
 * TODO: Doc! (sample)
 *
 * <p><b>IMPORTANT:</b></br>
 * Result is write into your classes directory.
 * You can set a new File for output using {@link #setOutputFile(File)}
 * </p>
 *
 * @author Claude CHOISNET
 */
public class I18nPropertyResourceBundleAutoUpdate
    extends AbstractI18nPropertiesResourceAutoUpdate
{
    private static final long serialVersionUID = 1L;
    private transient static Logger slogger = Logger.getLogger(I18nPropertyResourceBundleAutoUpdate.class);
    /** @serial */
    private File outputFile;

//    /**
//     * @param i18n
//     */
//    public I18nPropertyResourceBundleAutoUpdate(
//            I18nAutoUpdateInterface i18n
//            )
//    {
//        super( i18n, null, null );
//    }

    /**
     * @param i18nAutoUpdateInterface
     * @param autoI18nTypes
     * @param exceptionHandler
     * @param eventHandler
     * @param autoI18nAttributes
     * @param bundleAttributes
     */
    public I18nPropertyResourceBundleAutoUpdate(
            I18nAutoUpdateInterface                             i18nAutoUpdateInterface,
            AutoI18nTypes                                       autoI18nTypes,
            AutoI18nExceptionHandler                            exceptionHandler,
            AutoI18nEventHandler                                eventHandler,
            EnumSet<AutoI18n.Attribute>                         autoI18nAttributes,
            EnumSet<AbstractI18nResourceAutoUpdate.Attribute>   bundleAttributes
            )
    {
        super(  i18nAutoUpdateInterface,
                autoI18nTypes,
                exceptionHandler,
                eventHandler,
                autoI18nAttributes,
                bundleAttributes
                );
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

    private URL getResourceBundleBaseNameURL() throws MalformedURLException
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
     * @see #setOutputFile(File)
     */
    @Override
    public OutputStream getResourceBundleOutputStream() throws IOException
    {
        File file;

        if( outputFile == null ) {
            URL url = getResourceBundleBaseNameURL();
            URI uri;

            try {
                uri = url.toURI();
            }
            catch( URISyntaxException e ) {
                throw new IOException("URISyntaxException for:" + url, e);
            }

            file = new File( uri );
        }
        else {
            file = outputFile;
        }

        slogger.info( "getResourceBundleOutputStream() write to: " + file );

        return new FileOutputStream( file );
    }

    /**
     * Force to write result into a specific file
     *
     * @param outputFile the outputFile
     */
    public void setOutputFile( File outputFile )
    {
        this.outputFile = outputFile;
    }
}
