package com.googlecode.cchlib.i18n.builder;

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
import com.googlecode.cchlib.i18n.AutoI18n;
import com.googlecode.cchlib.i18n.AutoI18nEventHandler;
import com.googlecode.cchlib.i18n.AutoI18nExceptionHandler;
import com.googlecode.cchlib.i18n.AutoI18nTypes;
import com.googlecode.cchlib.i18n.I18nInterface;

/**
 * Implementation of {@link AutoI18n} that allow to build initial resource
 * file for localization.
 *
 * <p><b>IMPORTANT:</b></br>
 * Result is write into your classes directory.
 * You can set a new File for output using {@link #setOutputFile(File)}
 * </p>
 */
public class I18nPropertyResourceBundleAutoUpdate
    extends AbstractI18nPropertiesResourceAutoUpdate
{
    private static final long serialVersionUID = 1L;
    private transient static Logger LOGGER = Logger.getLogger(I18nPropertyResourceBundleAutoUpdate.class);
    /** @serial */
    private File outputFile;

    public I18nPropertyResourceBundleAutoUpdate(
        I18nAutoUpdateInterface i18nAutoUpdateInterface,
        AutoI18nTypes autoI18nDefaultTypes,
        AutoI18nTypes autoI18nForceTypes,
        AutoI18nExceptionHandler handler,
        AutoI18nEventHandler eventHandler,
        EnumSet<com.googlecode.cchlib.i18n.AutoI18n.Attribute> autoI18nAttributes,
        EnumSet<Attribute> bundleAttributes)
    {
        super(i18nAutoUpdateInterface, autoI18nDefaultTypes, autoI18nForceTypes,
                handler, eventHandler, autoI18nAttributes, bundleAttributes);
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

        LOGGER.info( "getResourceBundleInputStream() read from: " + url );

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

        LOGGER.info( "getResourceBundleOutputStream() write to: " + file );

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
